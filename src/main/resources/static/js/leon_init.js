let leon, controll;

function init() {
    generateCanvas();

    controll = {
        color: {},
        roundCap: true,
        multiply: true,

        drawing: () => {
            let i, total = leon.drawing.length;
            for (i = 0; i < total; i++) {
                TweenMax.killTweensOf(leon.drawing[i]);
                TweenMax.fromTo(leon.drawing[i], 2, {
                    value: 0
                }, {
                    value: 1,
                    ease: Cubic.easeOut
                });
            }
        }
    };

    leon = new LeonSans({
        text: 'DataPortal',
        color: ['#000000'],
        size: 42,
        weight: 818,
    });
    requestAnimationFrame(animate);
    controll.drawing(0);
}

function animate(t) {
    requestAnimationFrame(animate);

    ctx.clearRect(0, 0, sw, sh);
    ctx.lineCap = "round";

    const x = (sw - leon.rect.w) / 2;
    const y = (sh - leon.rect.h) / 2;
    leon.position(x, y);

    leon.drawColorful(ctx);
}
