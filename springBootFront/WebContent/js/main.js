//hovered
let list = document.querySelectorAll('.navigation li a');
function activeLink(){
    list.forEach((item)=>
    item.classList.remove('hovered'));
    this.classList.add('hovered');
}
list.forEach((item) =>
item.addEventListener('mousevover',activeLink))
//menu
let toggle = document.querySelector('.toggle');
let navigation = document.querySelector('.navigation');
let main = document.querySelector('.main');

toggle.onclick =function(){
    navigation.classList.toggle('active')
    main.classList.toggle('active')
}
