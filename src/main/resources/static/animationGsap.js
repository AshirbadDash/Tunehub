var tl=gsap.timeline();

tl.from("#div",{
    delay:0.5,
    y: -30,
    opacity:0,
    duration: 2,
    stagger: 0.3,
    
})

var initialPath = `M 10 100 Q 500 100 990 100`
var finalPath = `M 10 100 Q 500 100 990 100`

var string = document.querySelector("#string")

string.addEventListener("mousemove", function (dets) {
  const rect = string.getBoundingClientRect();
  const y = dets.clientY - rect.top;
  path = `M 10 100 Q 500 ${y} 990 100`;

  gsap.to("#string path", {
    attr: { d: path },
    duration: 0.3,
    ease: "power3.out",
  });
});  
string.addEventListener("mouseleave", function () {
    gsap.to("#string path", {
        attr: { d: finalPath },
        duration: 1.5,
        ease:"elastic.out(1, 0.2)"

    })
}) 
 