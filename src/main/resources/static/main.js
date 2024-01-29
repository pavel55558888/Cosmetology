document.addEventListener("DOMContentLoaded", function() {
    var dropdown = document.querySelector('.dropdown');
    var dropdownContent = document.querySelector('.dropdown-content');

    function checkHeight() {
        if (dropdownContent.offsetHeight > window.innerHeight) {
            dropdownContent.style.columnCount = "2";
            dropdownContent.style.columnGap = "20px";
            dropdownContent.style.marginLeft = "-200px";
        } else {
            dropdownContent.style.columnCount = "1";
            dropdownContent.style.marginLeft = "0";
        }
    }

    dropdown.addEventListener('mouseenter', checkHeight);
    window.addEventListener('resize', checkHeight);

    checkHeight();
});


setTimeout(function(){
    document.querySelector('.custom-alert').classList.add('hidden');
}, 1000);


window.onload = function() {
    var scrollPosition = localStorage.getItem('scrollPosition');
    if (scrollPosition) {
        window.scrollTo(0, scrollPosition);
        localStorage.removeItem('scrollPosition');
    }
}
window.onbeforeunload = function() {
    localStorage.setItem('scrollPosition', window.scrollY || document.documentElement.scrollTop);
}
