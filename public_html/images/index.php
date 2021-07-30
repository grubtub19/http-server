<!DOCTYPE html>
<?php
$parent = __DIR__;
chdir(getcwd() . "/public_html"); ?>
<html lang="en">
<?php include getcwd()."/templates/head.php"; ?>
<main>
	<?php include getcwd()."/templates/header.php"; ?>

	<body style="background:black;">
		<div class="outer-box">
			<div class="container">
				<?php include getcwd()."/templates/gallery.php"; ?>
			</div>
		</div>
	</body>
	<?php include getcwd()."/templates/scripts.php"; ?>
</main>

</html>
