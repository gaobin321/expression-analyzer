package neu.sxc.expression.test;

import junit.framework.TestCase;
import neu.sxc.expression.Expression;
import neu.sxc.expression.ExpressionFactory;
import neu.sxc.expression.lexical.LexicalException;
import neu.sxc.expression.syntax.ArgumentsMismatchException;
import neu.sxc.expression.syntax.SyntaxException;
import neu.sxc.expression.syntax.VariableNotInitializedException;
import neu.sxc.expression.tokens.Valuable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TestSyntax extends TestCase{
	
	private ExpressionFactory factory = ExpressionFactory.getInstance();
	
	public void testExpression() throws IOException{

		//在填写文件路径时，一定要写上具体的文件名称（xx.txt），否则会出现拒绝访问。
		//File file = new File("test/source.txt");
		File file = new File("/Users/wenba/IdeaProjects/expression-analyzer/src/test/java/source.txt");
		if(!file.exists()){
			//先得到文件的上级目录，并创建上级目录，在创建文件
			file.getParentFile().mkdir();
			try {
				//创建文件
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		//Expression expression = new Expression(new FileInputStream("test/source.txt"));
		Expression expression = new Expression(new FileInputStream("/Users/wenba/IdeaProjects/expression-analyzer/src/test/java/source.txt"));
		expression.lexicalAnalysis();
		Valuable evaluate = expression.evaluate();
		Boolean booleanValue = evaluate.getBooleanValue();
		System.out.println(booleanValue);
		Object value = evaluate.getValue();
		System.out.println(value);
		evaluate(expression);
//		Valuable a = expression.getVariableValue("a");
//		Printer.println(a.getValue());
	}
	
	public void testArithmetic() {
		Expression expression = factory.getExpression("a=a+1;");
		expression.initVariable("a", 2);
		
		expression.lexicalAnalysis();
		
		System.out.println("result:" + expression.evaluate().getValue());
		System.out.println("a = " + expression.getVariableValueAfterEvaluate("a").getValue());
		System.out.println("-------------------------------------");
		expression.initVariable("a", 5);
		System.out.println("result:" + expression.evaluate().getValue());
		System.out.println("a = " + expression.getVariableValueAfterEvaluate("a").getValue());
		System.out.println("-------------------------------------");
	}
	
	public void testCompare() {
		Expression expression = factory.getExpression("a=1<2 && 2>=3; b='a'<='b';" +
				"c=[2011-01-01]<=[2011-01-02]; d='a'!='a';");
		evaluate(expression);
	}
	
	public void testBoolean() {
		Expression expression = factory.getExpression("(1+2)>2 && !2>1 || TRUE;");
		evaluate(expression);
	}
	
	public void testFunction() {
		Expression expression = factory.getExpression("1 + max(1,abs(-2)) + abs(-1);");
		evaluate(expression);
	}
	
	public void testGetDate() {
		Expression expression = factory.getExpression("getDate();");
		expression.addFunction(new CurrentDate());
		evaluate(expression);
	}
	
	private void evaluate(Expression expression) {
		try {
			Printer.println(System.currentTimeMillis());
			expression.reParseAndEvaluate();
			Printer.println(System.currentTimeMillis());
		} catch (LexicalException e) {
			e.printStackTrace();
		} catch (VariableNotInitializedException e) {
			e.printStackTrace();
		} catch (ArgumentsMismatchException e) {
			e.printStackTrace();
		} catch (SyntaxException e) {
			e.printStackTrace();
		}
		PrintExpression.printExp(expression);
	}
}
