import { Component, OnInit } from '@angular/core';
import { Curso } from 'src/app/models/curso.model';
import { TemaCurso } from 'src/app/models/tema.curso.model';
import { Tema } from 'src/app/models/tema.model';
import { CursoService } from 'src/app/services/curso.service';
import { StorageService } from 'src/app/services/storage.service';
import { TemaService } from 'src/app/services/tema.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-set-guias-estudio',
  templateUrl: './set-guias-estudio.component.html',
  styleUrls: ['./set-guias-estudio.component.css']
})
export class SetGuiasEstudioComponent implements OnInit{
  listaCursos: Curso[] = [];
  listaTemas: TemaCurso[] = [];
  cursoSeleccionado = 1;
  temasSeleccionados: TemaCurso[] = [];
  listaCodigos: number[] = [];
  selectCursos : HTMLSelectElement | undefined = undefined
  selectTemas : HTMLSelectElement | undefined = undefined
  botonAgregarTema : HTMLButtonElement | undefined = undefined
  botonAsignarTemas : HTMLButtonElement | undefined = undefined
  quiereAsignarTemas : Boolean = false

  constructor(private storageService: StorageService, private cursoService: CursoService, private temaService: TemaService) {}
  ngOnInit(): void {
    this.storageService.comprobarSesion();
    this.storageService.denegarAcceso("ALUMNOyDOCENTE");
    this.llenarCursos();
    this.llenarTemas();
    window.addEventListener("load", () =>{
      this.selectCursos = document.getElementById("nombreCurso") as HTMLSelectElement
      this.selectTemas = document.getElementById("nombreTema") as HTMLSelectElement
      this.botonAgregarTema = document.getElementById("agregarTema") as HTMLButtonElement
      this.botonAsignarTemas = document.getElementById("asignarTemas") as HTMLButtonElement



       this.botonAgregarTema.addEventListener("click", () => {
        let indexSeleccionado = this.selectTemas?.selectedIndex
        let itemSeleccionado = this.selectTemas?.childNodes.item(indexSeleccionado!!) as HTMLOptionElement
        this.agregarTema(Number.parseInt(itemSeleccionado.value))
      })

      this.botonAsignarTemas.addEventListener("click", () => {
        if (this.temasSeleccionados.length == 0){
          this.mensajeErrorNoTemas()
        }
        else{
          let indexCursoSeleccionado = this.selectCursos?.selectedIndex
          let itemCursoseleccionado = this.selectCursos?.childNodes[indexCursoSeleccionado!!] as HTMLOptionElement
          this.temasSeleccionados.forEach(elemento => this.listaCodigos.push(elemento.codTema!!))
          this.preguntaAsignacion(Number.parseInt(itemCursoseleccionado.value), itemCursoseleccionado.innerText!!)
        }
      })

       this.selectCursos.addEventListener("change", () => {
        this.temasSeleccionados = []
      })
    })
  }
  //ngAfterViewInit + (click) event....

  /*
    Planeado para tercer sprint, probablemente haya una mejor forma de hacerlo sin checks.
  */

  llenarCursos(): void{
    this.cursoService.getCursos().subscribe({
      next: (data) =>{
        this.listaCursos = data;
      },
      error: (err) =>{
        this.mensajeError();
      }
    })
  }

  llenarTemas(): void{
    this.temaService.obtenerTemas().subscribe({
      next: (data) => {
        this.listaTemas = data;
      },
      error: (err) =>{
        this.mensajeError();
      }
    })
  }

  agregarTema(codTema : number) : void{
    let temaEncontrado = this.listaTemas.find(tema => tema.codTema == codTema) || new TemaCurso
    let enLista = this.temasSeleccionados.includes(temaEncontrado)
    if (!enLista){
      this.temasSeleccionados.push(temaEncontrado!!)
    }
  }

  quitarTema(codTema : number) : void{
    let listadoTemasFiltrados = this.temasSeleccionados.filter(tema => tema.codTema != codTema)
    this.temasSeleccionados = listadoTemasFiltrados

  }

  asignarTemas(listadoCodTemas : number[], codCurso : number) : void{
    this.temaService.asignarTemasCursos(listadoCodTemas, codCurso).subscribe({
      next: (data) =>{
        console.log(data)
        this.mensajeAsignado()

      },
      error: () => {
        this.mensajeErrorAsignado()
      }
    })

  }

  /*setCurso(curso: number | any): void{
    this.cursoSeleccionado = curso;
    console.log(this.cursoSeleccionado);
    this.setTemas(curso);
  }

  setTemas(curso: number): void{ //Llenar temas de curso en especifico.
    this.temaService.obtenerTemaPorCurso(curso).subscribe({
      next: (data) => {
        this.temasSeleccionados = data;
        console.log(this.temasSeleccionados);
        this.setCheck();
      },
      error: (err) =>{
        console.log(err)
      }
    })
  }

  setCheck(): void{
    this.listaTemas.forEach((e) =>{
      this.temasSeleccionados.forEach((x) =>{
        if(this.temasSeleccionados.find(i => i.codTema === e.codTema)){
          e.checked = true;
        }
        else{
          e.checked = false;
        }
      })
    })
    this.listaCodigos.length = 0;
    this.temasSeleccionados.forEach((i) =>{
      var cod: number = +i.codTema!;
      this.listaCodigos.push(cod);
    })
    console.log(this.listaCodigos);
  }

  checkChange(codTema: number | any){
    var cod: number = +codTema;
    this.listaCodigos.push(cod);
    console.log(this.listaCodigos);
  }

  uncheckAll(): void{
    this.listaTemas.forEach((e) =>{
      e.checked = false;
    })
  }*/

  private preguntaAsignacion(codCurso : number, nombreCurso : string): void{
    let listadoTemasMensaje = ""
    this.temasSeleccionados.forEach((tema, indice) => {
      if (indice == 0){
        listadoTemasMensaje += `${tema.nombre}`
      }
      else{
        listadoTemasMensaje += `, ${tema.nombre}`
      }
    })
    Swal.fire({
      title: `¿Desea asignar los siguientes temas al curso de ${nombreCurso}?`,
      text: listadoTemasMensaje,
      showDenyButton: true,
      confirmButtonText: "SÍ",
      denyButtonText: "NO"
    }).then((result) => {
      if (result.isConfirmed) {
        this.asignarTemas(this.listaCodigos, codCurso)
      }
    });
  }
  private mensajeAsignado(): void{
    Swal.fire({
      title: "Guías Asignadas al Curso",
      text: "Usted ha asignado las guías correctamente",
      icon: "success"
    }).then(() => {
      this.limpiarTodo()
    })
  }

  private mensajeErrorAsignado(): void{
    Swal.fire({
      title: "Ups! Ha sucedido un error.",
      text: "No se han podido asignar los temas al curso",
      icon: "error"
    })
  }

  private mensajeErrorNoTemas(): void{
    Swal.fire({
      title: "Sin Temas Seleccionados!",
      text: "No hay Temas seleccionados, por favor selccione al menos un tema para asignarlo al curso",
      icon: "error"
    })
  }

  public mensajeError(): void{
    Swal.fire({
      title: "Ups! Ha sucedido un error.",
      text: "No se encontraron guías de estudio asociadas a ningún curso.",
      icon: "error"
    })
  }

  private limpiarTodo(){
    this.temasSeleccionados = []
    this.listaCursos = []
    this.listaTemas = []
    this.llenarCursos()
    this.llenarTemas()
  }
}
