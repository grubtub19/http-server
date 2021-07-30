<div class="row responsive-columns">
  <?php
  $files = glob($parent."\*.{jpg,gif,png}", GLOB_BRACE);
  $i = 0;
  if(preg_match('/public_html\\\\(.*)/',$parent,$matches)) {
    $match = $matches[1];
  } else {
    $match = "";
  }
  shuffle($files);
  foreach($files as $file):
    $file = basename($file);
    if($match != "") {
    $link = "/" . $match . "/" . $file;
  } else {
    $link = "/" . $file;
  }
  ?>
  <?php if(strpos(pathinfo($file)['basename'], ' ')) { continue; } ?>
  <?php if(in_array(pathinfo($file)['extension'],array("png","gif","jpg","jpeg"))) : ?>
  <div class="col s12">
    <div class="card transparent">
      <div class="card-image">
        <a href="<?php echo $link ?>">
          <img class="image" src="<?php echo $link ?>">
        </a>
        <span class="card-title">
          <a class="btn down-icon waves-effect waves-light" href="<?php echo $link ?>" download><i class="material-icons">cloud_download</i></a>
        </span>
      </div>
    </div>
  </div>
  <?php elseif(in_array(pathinfo($file)['extension'],array("webm","mp4"))): ?>
  <?php $i += $i + 4; ?>
  <div class="col s12">
    <div class="card transparent">
      <div class="card-image">
        <a href="<?php echo $link ?>">
          <video autoplay loop muted>
            <source src="<?php echo $link ?>" type="video/<?php echo pathinfo($file)['extension']?>">
          </video>
        </a>
        <span class="card-title">
          <a class="btn down-icon waves-effect waves-light" href="<?php echo $link ?>" download><i class="material-icons">cloud_download</i></a>
        </span>
      </div>
    </div>
  </div>
  <?php endif;?>
  <?php if($i > 8){ break;} else {$i++;}?>
  <?php endforeach; ?>
  <a href="index1.html">
    <div style="height: 1rem; width: 1rem;"></div>
  </a>
</div>
<div class="fixed-action-btn" style="bottom: 45px; right: 24px;">
  <a href="." class="btn-floating btn-large red">
    <i class="material-icons">add</i>
  </a>
</div>
