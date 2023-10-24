import { IEstudiante } from "./IEstudiante";
import { IMateria } from "./IMateria";
import { ITag } from "./ITag";

export interface IGrupo {
  id?: string;
  parKey?: string;
  materia?: IMateria;
  miembros?: IEstudiante[];
  intereses?: ITag[];

  [key: string]: any;
}
