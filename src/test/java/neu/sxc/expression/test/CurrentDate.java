package neu.sxc.expression.test;

import java.util.Calendar;
import java.util.Date;

import neu.sxc.expression.syntax.function.Function;
import neu.sxc.expression.tokens.DataType;
import neu.sxc.expression.tokens.Valuable;

public class CurrentDate extends Function {

	@Override
	//返回函数名,函数名不能为空
	public String getName() {
		return "getDate";
	}
	
	@Override
	//返回参数个数,当返回值小于0时,表示参数个数不限
	public int getArgumentNum() {
		//定义无参函数时,返回0,且构造函数中无需传入参数类型数组
		return 0;
	}
	
	@Override
	//返回函数参数类型数组
	//当参数个数不限时,所有参数类型必须相同,本方法必须提供一个参数类型
	public DataType[] getArgumentsDataType() {
		return null;
	}
	
	@Override
	protected Object executeFunction(Valuable[] arguments) {
		Calendar date = Calendar.getInstance();
		date.setTime(new Date());
		return date;
	}
}
