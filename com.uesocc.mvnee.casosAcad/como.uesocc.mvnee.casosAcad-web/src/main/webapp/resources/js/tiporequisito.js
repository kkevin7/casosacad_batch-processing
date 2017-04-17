


function mostrarformulario(){
            document.getElementById('menucontainer').classList.remove('hiden');
             document.getElementById('menu:ccbtn:btnCrear').classList.remove('hiden');
            document.getElementById('menu:ccbtn:btnEditar').classList.add('hiden');
                        document.getElementById('menu:ccbtn:btnEliminar').classList.add('hiden');
    
    
} 
 function btneditar(){
            document.getElementById('menucontainer').classList.remove('hiden');
            document.getElementById('menu:ccbtn:btnCrear').classList.add('hiden');
            document.getElementById('menu:ccbtn:btnEditar').classList.remove('hiden');
                        document.getElementById('menu:ccbtn:btnEliminar').classList.remove('hiden');


            
           
    
    
} 

function cerrar(){
             document.getElementById('menucontainer').classList.add('hiden');
               

}

function ocultar(){
        document.getElementById("msgs").style.display="none";

}

function habilitar(btn){
    document.getElementById(btn).disabled=false;
        document.getElementById("msgs").style.display="block";

    
    
}
function deshabilitar(btn){
      document.getElementById(btn).disabled=true;
    
    
}

function validar(fieldone,fieldtwo){
              var  campouno,campodos;
              campouno=document.getElementById(fieldone).value;
              campodos=document.getElementById(fieldtwo).value;
              if(campouno.length === 0 || campodos.length === 0 ){
                 window.alert("Existen campos vacios");
                  
              }
              else{
                  if(campouno.toString().charAt(0) !==" " && campodos.toString().charAt(0) !==" "){
//                      window.alert("Exito");
                  }
                  else{
                       window.alert("Existen campos vacios");   
                  }
                  
                    
              }
          }


