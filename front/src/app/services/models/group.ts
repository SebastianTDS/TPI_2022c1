import { IuserDto } from "./iuserDto";

export interface Group{

    id?: number;
    name?: string;
    image?: string;
    description?: string;
    closed?: boolean;
    maxNumberOfStudents?: number;
    numberOfStudents?: number;
    shift?: string;
    career?: string;
    subject?: string;
    assessment?: string;
    students?: IuserDto[];
    idUserAdmin?: string;

    [key: string]: any;

}