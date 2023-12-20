<!DOCTYPE html>
<html lang="esS">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/bootstrapValidator.js"></script>
<script type="text/javascript" src="js/global.js"></script>

<link rel="stylesheet" href="css/bootstrap.css" />
<link rel="stylesheet" href="css/dataTables.bootstrap.min.css" />
<link rel="stylesheet" href="css/bootstrapValidator.css" />
<title>Ejemplos de CIBERTEC - Jorge Jacinto</title>
</head>
<body>
	<form id ="id_form">
	<div class="container">
		<div class="row" style="margin-top: 3%">
			<div class="col-md-12" align="center">
				<button type="button" class="btn btn-danger" id="id_btn_reporte">PDF</button>
			</div>
		</div>
	</div>
	</form>
	
<script type="text/javascript">

$("#id_btn_reporte").click(function(){
	$("#id_form").attr('action', 'verSubReportePDF');
	$("#id_form").submit();
});

</script>
</body>
</html>








