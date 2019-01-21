<html>
<head>
    <script src="jquery.min.js"></script>
</head>
<body>
<h2>Hello World!</h2>
</body>
<script>
  window.onload = function () {
    console.log("begin")
    $.ajax({
          url: "config.json"
        }
    ).done(function (data) {
      console.log(data)
    })

  }
</script>
</html>
