<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1"/>
    <meta name="theme-color" content="#000" />
    <meta name="pinterest" content="nopin" />

    <title>Hugebox</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
    <asset:stylesheet src="application.css"/>
</head>
<body id="launch-page">
    <div class="title">
        <div>
            <h1><g:message code="hugebox.caps" default="HUGEBOX"/></h1>
            <h2><g:message code="hugebox.slogan.caps" default="PLAY YOUR MUSIC EVERYWHERE"/></h2>
        </div>
    </div>

    <div class="wave">
        <canvas id="wave"></canvas>
    </div>

    <div class="login">
        <oauth2:connect provider="google">
            <asset:image src="btn_google_light_normal_ios.svg"/>
            <span><g:message code="user.login.google" default="Login with Google"/></span>
        </oauth2:connect>
    </div>

    <script>
        var canvas = document.getElementById("wave");
        var context = canvas.getContext("2d");
        canvas.width = window.innerWidth;
        canvas.height = window.innerHeight / 2;
        window.requestAnimationFrame(draw);
        //var oldTime = performance.now();

        window.onresize = function () {
            canvas.width = window.innerWidth;
            canvas.height = window.innerHeight / 2;
        };

        function draw() {
            var time = performance.now();
            context.clearRect(0, 0, canvas.width, canvas.height);

            //context.fillStyle="#f33";
            //context.fillText(1000 / (time - oldTime), 10, 10);

            var timescale = time / (1 / canvas.width) / 1000;
            var timescaleVaried = time / (1 / canvas.width) / 2000;
            var canvasHalfHeight = canvas.height / 2;

            for(var i = 1; i < canvas.width; i++)
            {
                context.fillStyle="#f33";
                context.fillRect(i, Math.sin((i + timescale) / (canvas.width / 2)) * canvas.height / 2.5 + canvasHalfHeight, 1, 1);
                context.fillRect(i, Math.sin((i + timescaleVaried) / (canvas.width / 2)) * canvas.height / 2.5 + canvasHalfHeight, 1, 1);

                var color = Math.round((i / canvas.width) * 255);
                context.fillStyle="RGB(255, " + color + ", " + color + ")";
                context.fillRect(i, Math.sin((i + timescale) / (canvas.width / 3)) * canvas.height / 4 + canvasHalfHeight, 1, 1);
                context.fillRect(i, Math.sin((i + timescale + 20) / (canvas.width / 3)) * canvas.height / 4 + canvasHalfHeight, 1, 1);
                context.fillRect(i, Math.sin((i + timescale - 20) / (canvas.width / 3)) * canvas.height / 4 + canvasHalfHeight, 1, 1);
            }

            //oldTime = performance.now();

            window.requestAnimationFrame(draw);
        }
    </script>
</body>
</html>
