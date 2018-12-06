package neu.sxc.expression.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
import junit.framework.TestCase;
import neu.sxc.expression.Expression;
import neu.sxc.expression.ExpressionFactory;
import neu.sxc.expression.lexical.LexicalAnalyzer;
import neu.sxc.expression.lexical.LexicalException;
import neu.sxc.expression.tokens.DataType;
import neu.sxc.expression.tokens.TerminalToken;
import neu.sxc.expression.tokens.Valuable;
import neu.sxc.expression.utils.ValueUtil;


public class TestLexical extends TestCase {
	private ExpressionFactory factory = ExpressionFactory.getInstance();

	public void testDemo() {
		//创建表达式
		/*
		*
		* 	lexical : 词法
		* 	syntax : 语法
		* 	tokens : 符号
		*
		* 	类Expression的实例表示一个表达式,
		* 	可通过Expression的构造函数创建或者使用ExpressionFactory创建
		*
		* 	表达式由语句组成,每个语句必须由分号结尾.
		*
		* 	定义表达式时可包含一个语句或多个语句,解析结果是被执行的最后一个语句的结果
		*
		* 	Expression有三个构造函数,可分别接受参数 String, InputStream, Reader
		*
		* 	1. 使用构造函数创建
		* 		Expression exp = new Expression("a=1; a+a*100;");
		*
		* 	2.使用ExpressionFactory创建
		*		ExpressionFactory factory = ExpressionFactory.getInstance();
		*		Expression exp = factory.getExpression("a=1; a+a*100;");
		*
		*	3.执行表达式
		*		通过调用Expression的evaluate()方法执行表达式
		*		ExpressionFactory factory = ExpressionFactory.getInstance();
		*		Expression exp = factory.getExpression("a=1; a+a*100;");
		*		//词法解析
		*		exp.lexicalAnalysis();
		*		//执行
		*		Valuable result = exp.evaluate();
		*
		*		注意:执行evaluate()的前提是已经执行词法分析,否则抛出异常
		*			或者直接调用Expression的reParseAndEvaluate()方法
		*			reParseAndEvaluate()方法每次都重新执行词法分析
		*
		*		表达式的执行结果以接口Valuable表示,Valuable提供如下方法:
		*			public DataType getDataType();
		*			public int getIndex();
		*			public BigDecimal getNumberValue();
		*			public String getStringValue();
		*			public Character getCharValue();
		*			public Calender getDateValue();
		*			public Boolean getBooleanValue();
		*			public Object getValue();
		*/

		Expression exp = new Expression("a=1; a+a*100;");

		exp.lexicalAnalysis();
		/*List<TerminalToken> terminalTokens = exp.lexicalAnalysis();
		if(terminalTokens != null && terminalTokens.size() > 0) {
			for (TerminalToken tt : terminalTokens) {
				System.out.println(tt.toString());
			}
		}*/
		Valuable result = exp.evaluate();
		Object value = result.getValue();
		System.out.println(value);

	}

	public void testDemo1() {
		ExpressionFactory factory = ExpressionFactory.getInstance();
		Expression exp = factory.getExpression("a=1; a+a*100;");

		exp.lexicalAnalysis();

		Valuable evaluate = exp.evaluate();

		BigDecimal numberValue = ValueUtil.getNumberValue(evaluate);

		System.out.println(numberValue);

		DataType dataType = evaluate.getDataType();
		System.out.println(dataType);
		Object value = evaluate.getValue();
		System.out.println(value);
	}

	/**
	 * 计算额外积分金额 规则如下: 订单原价金额
	 * 100以下, 不加分
	 * 100-500 加100分
	 * 500-1000 加500分
	 * 1000 以上 加1000分
	 */
	public void testInvest() {

		/*Expression exp = factory.getExpression("(pChannel == \"客服\") ? \"S+,中\" : " +
				"(pChannel == \"SEM\" && pChannel1 == \"学好网\" && pChannel2 == \"百度\" && pAddress == \"北京\") ? \"S+,高\" : " +
				"(pChannel == \"SEM\" && pChannel1 == \"学好网\" && pAddress == \"北京\") ? \"S+,高\" : " +
				"(pChannel == \"DSP\" && pChannel1 == \"朋友圈\" && pChannel2 == \"账号1\" && pAddress == \"上海\") ? \"S,中\" : " +
				"(pChannel == \"DSP\" && pChannel1 == \"朋友圈\" && pChannel2 == \"账号2\" && pAddress == \"北京\") ? \"A+,中\" : " +
				"(pChannel == \"NW对外投放\" && pChannel1 == \"公众号\" && pAddress == \"天津\") ? \"S,中\" : " +
				"(pChannel == \"NW对外投放\" && pChannel1 == \"公众号\") ? \"A,中\" : " +
				"(pChannel == \"DSP\") ? \"S,低\" : \"ERROR\"");

		exp.lexicalAnalysis();
		Valuable result = exp.evaluate();
		Object value = result.getValue();
		System.out.println(value);*/

		//表达式
		List<String> expressions = new ArrayList<String>();

		expressions.add("ifthen((pChannel == \"客服\"), \"S+,中\");");
		expressions.add("ifthen((pChannel == \"SEM\" && pChannel1 == \"学好网\" && pChannel2 == \"百度\" && pAddress == \"北京\"), \"S+,高\");");
		expressions.add("ifthen((pChannel == \"SEM\" && pChannel1 == \"学好网\" && pAddress == \"北京\"), \"S+,高\");");
		expressions.add("ifthen((pChannel == \"DSP\" && pChannel1 == \"朋友圈\" && pChannel2 == \"账号1\" && pAddress == \"上海\"), \"S,中\");");
		expressions.add("ifthen((pChannel == \"DSP\" && pChannel1 == \"朋友圈\" && pChannel2 == \"账号2\" && pAddress == \"北京\"), \"A+,中\");");
		expressions.add("ifthen((pChannel == \"NW对外投放\" && pChannel1 == \"公众号\" && pAddress == \"天津\"), \"S,中\");");
		expressions.add("ifthen((pChannel == \"NW对外投放\" && pChannel1 == \"公众号\"), \"A,中\");");
		expressions.add("ifthen((pChannel == \"DSP\"), \"S,低\");");


		List<Map<String,Object>> list = new ArrayList<>();

		Map<String,Object> map1 = new HashMap<>();
		Map<String,Object> map2 = new HashMap<>();
		Map<String,Object> map3 = new HashMap<>();

		map1.put("pChannel","DSP");
		list.add(map1);

		map2.put("pChannel","NW对外投放");
		map2.put("pChannel1","公众号");
		map2.put("pAddress","天津");
		list.add(map2);

		map3.put("pChannel","SEM");
		map3.put("pChannel1","学好网");
		map3.put("pAddress","北京");
		list.add(map3);

		Expression exp;
		Valuable result = null;
		for(int i = 0; i < list.size(); i++) {
			if(null != expressions && expressions.size() > 0) {
				for(String s : expressions) {
					exp = factory.getExpression(s);
					exp.lexicalAnalysis();


					for(String str : list.get(i).keySet()) {
						exp.initVariable(str,list.get(i).get(str));
					}
				/*exp.initVariable("pChannel","DSP");
				exp.initVariable("pChannel","DSP");*/

					result = exp.evaluate();
					if(null == result || "".equals(result)) {
						continue;
					}else {
						Object value = result.getValue();
						System.out.println("结果:" + value);
					}
					break;
				}
			}
		}




		/*exp = factory.getExpression("ifthen(pChannel == \"客服\", \"S+,中\");");
		exp = factory.getExpression("ifthen(pChannel == \"SEM\", \"S+,高\");");*/
		//Expression exp = factory.getExpression("ifthen(pChannel == \"SEM\", \"S+,高\");");
		//Expression exp = factory.getExpression("(pChannel == \"客服\") ? \"S+,中\"; : \"ERROR\";");
		//Expression exp = factory.getExpression("ifthen(2 > 1, 2);");
		/*exp.initVariable("pChannel","客服");

		exp.lexicalAnalysis();
		Valuable result = exp.evaluate();
		Object value = result.getValue();
		System.out.println(value);*/
	}

	/**
	 *	if(Boolean) endif
	 *	或
	 *	if(Boolean) else endif
	 *
	*/
	public void testExpression() {
		//Expression exp = factory.getExpression("if(2 > 1) 2; endif");
		//Expression exp = factory.getExpression("if(2 > 1) 2; else 1; endif");
		Expression exp = factory.getExpression("if(2 > 1) 2; else 1;");
		//Expression exp = factory.getExpression("if(2 > 1) a=abs(-3); if(false) a=a+1; else a=a+2; endif a=a+1; else if(false) a=9; endif endif");
		/*Expression exp = factory.getExpression("if(2 > 1)" +
													"a=abs(-3);" +
													"if(false)" +
														"a=a+1;" +
													"else " +
														"a=a+2;" +
													"endif" +
													"a=a+1;" +
												"else" +
													"if(2 > 1)" +
														"a=9;" +
													"endif" +
												"endif");*/
		exp.lexicalAnalysis();
		Valuable result = exp.evaluate();
		Object value = result.getValue();
		System.out.println("结果:" + value);
	}


	public void testNestExp() {
		//Expression exp = factory.getExpression("if(1 > 1) 2; endif");
		Expression exp = factory.getExpression("if(1 > 1) 2; else 1; endif");
		exp.lexicalAnalysis();
		Valuable result = exp.evaluate();
		Object value = result.getValue();
		System.out.println("结果:" + value);

		/*Expression exp = factory.getExpression("b+2");
		exp.lexicalAnalysis();
		Valuable result = exp.evaluate();
		Object value = result.getValue();
		System.out.println("结果:" + value);*/
	}

	public void testChinaExp() {
		Expression exp = factory.getExpression("如果(1 > 1) 那么 (2); 否则 (1);");
		//Expression exp = factory.getExpression("如果(2 > 1) 2; 否则 1; endif");
		exp.lexicalAnalysis();
		Valuable result = exp.evaluate();
		Object value = result.getValue();
		System.out.println("结果:" + value);
	}

	public void testNumber() {
		Expression expression = factory.getExpression("1 1.1 1.10 1e+2 1.1e-1");
		lexicalAnalysis(expression);
	}
	
	public void testDelimiter() {
		Expression expression = factory.getExpression("+-*/ >=<= ><,;&& ||!");
		lexicalAnalysis(expression);
	}
	
	public void testBoolean() {
		Expression expression = factory.getExpression("true false TRUE FALSE");
		lexicalAnalysis(expression);
	}
	
	public void testDate() {
		Expression expression = factory.getExpression("[2011-1-11] [2011-01-11] [2011-1-11 1:1:1] [2011-1-11 23:59:59]");
		lexicalAnalysis(expression);
	}
	
	public void testString() {
		Expression expression = factory.getExpression(" \"as\" ");
		lexicalAnalysis(expression);
	}
	
	public void testChar() {
		Expression expression = factory.getExpression(" 'a' ");
		lexicalAnalysis(expression);
	}
	
	public void testFunction() {
		Expression expression = factory.getExpression(" max abs ");
		lexicalAnalysis(expression);
	}
	
	public void testError() {
		Expression expression = factory.getExpression(" &2");
		lexicalAnalysis(expression);
	}

	//词法分析
	private void lexicalAnalysis(Expression expression) {
		LexicalAnalyzer la = new LexicalAnalyzer();
		try {
			List<TerminalToken> tokens = la.analysis(expression.getExpression(), expression.getFunctionDefinitions());
			PrintExpression.printTokens(tokens);
		} catch (LexicalException e) {
			e.printStackTrace();
		}
	}
}
