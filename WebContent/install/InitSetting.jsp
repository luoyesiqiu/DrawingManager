<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生作品管理系统安装</title>
<link href="../front/css/footer.css" rel="stylesheet">
<link href="../front/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="../front/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="../front/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
<h3>学生作品管理系统安装</h3>
<hr>
<h4>管理员</h4>
<div id="init-setting">
<form  method="post" action="../InitSetting">
<label  class="control-label">管理员用户名：</label>
<input type="text" name="admin_name" class="form-control" placeholder="用户名" value="admin"/>
<label  class="control-label">管理员密码：</label>
<input type="password" name="admin_pwd" class="form-control" placeholder="密码"/>
<hr>
<h4>其他设置</h4>
<label  class="control-label">学生专业（每个专业用“|”隔开）</label>
<input type="text" name="student_major" class="form-control" placeholder="学生专业（每个专业用“|”隔开）" value="美术学|服装设计|平面设计|室内设计|动画"/>
<hr>
<label  class="control-label">作品类型（每个类型用“|”隔开）</label>
<input type="text" name="drawing_type" class="form-control" placeholder="作品类型（每个类型用“|”隔开）" value="水彩画|水墨画|水粉画|油画|版画|工笔画|木刻|素描|书法|篆刻|雕塑"/>
<hr>
<label  class="control-label">获奖等级（每个等级用“|”隔开）</label>
<input type="text" name="prize_level" class="form-control" placeholder="获奖等级（每个等级用“|”隔开）" value="院级|校级|市级|省级|国家级|世界级"/>
<hr>
<label  class="control-label">年级（每个年级用“|”隔开）</label>
<input type="text" name="student_grade" class="form-control" placeholder="年级（每个年级用“|”隔开）" value="2017|2016|2015|2014"/>
<hr>
<input type="submit" name="init_setting_submit" class="col-md-1 btn btn-primary"  value="完成"/>
</form>
</div>
</div>
<script type="text/javascript">
$("#init-setting").submit(function(){
	
	  var list=$("#init-setting").find(".form-control");
	  	for(var i=0;i<list.length;i++){
	  		var val=list.get(i).value;
			if(val==null||val=="")
			{
				alert("信息都不能留空噢");
				return false;
			}

	  	}//for
		return true;
});</script>
</body>
</html>