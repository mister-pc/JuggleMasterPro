/*
* Javascript Juggling 0.1 Copyright (c) 2006 Boris von Loesch
*
* Permission is hereby granted, free of charge, to any person obtaining a 
* copy of this software and associated documentation files (the "Software"), 
* to deal in the Software without restriction, including without limitation 
* the rights to use, copy, modify, merge, publish, distribute, sublicense, 
* and/or sell copies of the Software, and to permit persons to whom the 
* Software is furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included 
* in all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
* THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING 
* FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
* DEALINGS IN THE SOFTWARE.
*/

	function Ball (animator, ball) {
		this.ball = ball;
		this.step = 0;
		this.ssw = 0;
		this.hand = -1;
		this.animate = animateMe;
		this.animator = animator;
	}

	function animateMe() {
		var animator = this.animator;
		if (this.ssw == 0) {
			this.ball.style.visibility = "hidden";
			return;
		}
		else if (this.step == 0) this.ball.style.visibility = "visible";
		var sideLength = (animator.contWidth - animator.ballSize)/2;
		var start = sideLength - (animator.style[0] * sideLength)/100;
		var end = sideLength + (animator.style[0] * sideLength)/100;
		var startToss = sideLength - (animator.style[2] * sideLength)/100;
		if (this.hand == 1) {
			var temp = start;
			start = end;
			end = temp;
			startToss = sideLength + (animator.style[2] * sideLength)/100;
		}
		if (this.ssw % 2 == 0) end = start;
		var pos;
		if (this.step < animator.dwellSteps)
			pos = interpolateBezier(this.step, animator.dwellSteps, start, 
				animator.yCoord + animator.style[1], 
				startToss, animator.yCoord + animator.style[3], -30);
		else 
			pos = getThrowCoord(this.step - animator.dwellSteps, 
				this.ssw * animator.resolution - animator.dwellSteps, animator.T, 
				startToss, animator.yCoord + animator.style[3], end, 
				animator.yCoord + animator.style[1], this.ssw);
		this.ball.style.left = pos[0] + 'px';
		this.ball.style.bottom = pos[1] + 'px';
		this.step += 1;
	}


/*
 * -------------------------------------------------------------------------------------------
 */

	function SiteswapAnimator(viewport) {
		this.id = SiteswapAnimator.instances.length;
		SiteswapAnimator.instances[this.id] = this;
		this.container = viewport;
		//Get the width of the container
		var css;
		if (this.container.currentStyle == null) css = window.getComputedStyle(this.container, null);
		else css = this.container.currentStyle;
		this.contWidth = parseInt(css.width);
		this.contHeight = parseInt(css.height);
		this.speed = 10;
		this.resolution = 20;
		this.dwell = 0.5;
		this.dwellSteps = Math.round(this.dwell * this.resolution);
		//Change this if you change the ball picture
		this.ballSize = 25;
		this.ballPicture = "ball.gif";
		this.yCoord = 30;
		this.T = 4;
		
		this.ssw = "0";
		this.number = 0;
		this.timer = null;
		this.balls = null;
		this.step = 0;
		this.sswStep = 0;
		this.maxThrow = 0;
		this.style = new Array(100,0,50,0);
		
		//Functions
		this.setSiteswap = setSiteswap;
		this.checkSiteswap = checkSiteswap;
		this.destroyBalls = destroyBalls;
		this.createBalls = createBalls;
		this.getHeight = getHeight;
		this.getNextBall = getNextBall;
		this.animate = animate;
		this.startAnimation = startAnimation;
		this.stopAnimation = stopAnimation;
	}
 	SiteswapAnimator.instances = new Array()	

		
	/**
	 * Creates and starts the siteswap animation
	 */
	function setSiteswap (ssw, resolution, style) {
		if (setSiteswap.arguments.length >= 3) {
			this.style = eval(style);
		}
		if (setSiteswap.arguments.length >= 2) {
			if (isNaN(parseInt(resolution)) || parseInt(resolution)<5) {
				alert ("Please enter a valid steps number (>4)");
				return;
			}
			else {
				this.resolution = parseInt(resolution);
				this.dwellSteps = Math.round(this.dwell * resolution);
			}
		}
		this.ssw = ssw;
		this.stopAnimation();
		this.destroyBalls();
		if (this.checkSiteswap() == false) alert (this.ssw+" is no valid siteswap!");
		else {
			//Control throw height
			this.T = 4;
			var h = Math.sqrt((this.contHeight - this.yCoord - this.ballSize)/
				((this.maxThrow - 1.0)*(this.maxThrow - 1.0)));
			if (h < this.T) this.T = h;
			this.createBalls();
			this.step = 0;
			this.sswStep = 0;
		}
	}

	/**
	 * Check siteswap and calculate number of balls
	 * and the maximal height
	 */
	function checkSiteswap() {
		if (this.ssw.length == 0) return false;
		var buf = new Array();
		var buffer;
		this.maxThrow = 0;
		var m = 0;
		for (var i = 0; i < this.ssw.length; i++)
			buf[i] = false;
		for (var i = 0; i < this.ssw.length; i++) {
			var s = parseInt(this.ssw.charAt(i));
			m += s;
			if (s > this.maxThrow) this.maxThrow = s;
			buffer = (s + i) % this.ssw.length;
			if (buf[buffer] == true) return false;
			else buf[buffer] = true;
		}
		this.number = m / this.ssw.length;
		return true;
	}
	
	
	function destroyBalls() {
		if (this.balls != null) {
			for (var i = 0; i < this.balls.length; i++) {
				this.container.removeChild(this.balls[i].ball);
			}
			this.balls = null;
		}
	}
	
	function createBalls () {
		this.balls = new Array(this.number);
		for (var i = 0; i < this.number; i++) {
			var img = document.createElement("img");
			img.className = "ball";
			img.src = this.ballPicture;
			img.id = "ball"+i;
			img.style.left = "0px";
			img.style.bottom = "0px";
			this.balls[i] = new Ball(this, img);
			this.container.appendChild(img);
		}
	}

	
	function getHeight(position) {
		return parseInt(this.ssw.charAt(position % this.ssw.length));
	}
	
	function getNextBall() {
		for (var i = 0; i < this.number; i++) {
			if (this.balls[i].step == this.balls[i].ssw * this.resolution) return i;
		}
		return -1;
	}
	
	function animate() {
		if (this.step % this.resolution == 0) {
			var height = this.getHeight(this.sswStep);
			if (height != 0) {
				var i = this.getNextBall();
				if (i == -1){
					this.stopAnimation();
					alert ("Fehler");
					return;
				}
				this.balls[i].ssw = height;
				this.balls[i].step = 0;
				if (this.sswStep % 2 == 0) this.balls[i].hand = -1;
				else this.balls[i].hand = 1;
			}
			this.sswStep++;
		}
		for (var i = 0; i < this.number; i++) {
			this.balls[i].animate();
		}
		this.step++;
	}
	
	function stopAnimation() {
		clearInterval(this.timer);
	}
	
	function startAnimation() {
		var o = this;
		this.balls[0].ball.style.visibility = "visible";
		this.timer = setInterval('SiteswapAnimator.instances[' + this.id + '].animate();', this.speed);
	}
	
/*
 * -------------------------------------------------------------------------------------------
 */

	/**
	 * Static function
	 */
	function interpolateBezier(step, cStep, startx, starty, endx, endy, oben) {
		var t = step / (cStep * 1.0);
		var u = 1 - t;
		var tuTriple = 3 * t * u;
		var c0 = u * u * u;
		var c1 = tuTriple * u;
		var c2 = tuTriple * t;
		var c3 = t * t * t;
		x = (startx + endx) / 2.0;
		y = (starty + endy) / 2.0 + oben;
		x = c0 * startx + c1 * x + c2 * x + c3 * endx;
		y = c0 * starty + c1 * y + c2 * y + c3 * endy;
		var pos = new Array(2);
		pos[0] = x; pos[1] = y;
		return pos;
	}

	/**
	 * Static function
	 */
	function getThrowCoord (step, cStep, T, startx, starty, endx, endy, height) {
		var t = (height - 1.0) * T;
		if (height == 3) t = (3.5 - 1.0) * T;
		var h = (9.81 * t * t) / 10.0;
		var st = 1.0*step / cStep;
		x = startx + st * (endx - startx);
		y = starty + (st * (endy - starty)) - 4.0 * h * (st * st - st);
		var pos = new Array(2);
		pos[0] = x;
		pos[1] = y;
		return pos;
	}
	