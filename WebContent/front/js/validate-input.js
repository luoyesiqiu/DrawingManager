/**
 * 该js用于验证用户输入
 */

/**
 * 判断是否为空输入
 */
function isEmpty(s){
	if(s==null||s==""){
		return true;
	}
	return null;
}
/**
 * 验证学号
 */
function validateStudentId(s)
{
	var pattern=/^[1-9][0-9]{5,19}$/;
	if (!pattern.exec(s)) 
		return false;
	return true;
}
/**
 * 验证管理员用户名
 * @param s
 * @returns
 */
function validateAdminName(s)
{
	var pattern=/^[a-zA-Z0-9]{3,14}$/;
	if (!pattern.exec(s)) 
		return false;
	return true;
}
/**
 * 验证密码
 * @param s
 * @returns
 */
function validatePwd(s)
{
	var pattern=/^[^\s]{5,20}$/;
	if (!pattern.exec(s)) 
		return false;
	return true;
}

/**
 * 验证学生姓名
 * @param s
 * @returns
 */
function validateStudentName(s)
{
	var pattern=/^[\u4E00-\u9FA5]{2,4}$/;
	if (!pattern.exec(s)) 
		return false;
	return true;
}

/**
 * 验证字符串长度
 * @param s
 * @returns
 */
function validateStringLen(s,min,max)
{
	if (s.length>=min&&s.length<=max) 
		return true;
	return false;
}