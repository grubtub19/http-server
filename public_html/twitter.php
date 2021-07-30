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
				<?php include getcwd()."/templates/accordian.php"; ?>
        <a class="twitter-timeline" href="https://twitter.com/TwitterDev?ref_src=twsrc%5Etfw">Tweets by TwitterDev</a> <script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>
			</div>
		</div>
	</body>
	<?php include getcwd()."/templates/scripts.php"; ?>
</main>

</html>
