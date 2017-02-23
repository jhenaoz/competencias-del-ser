import { IEmployee } from '../employee/employee.model';
import { Aptitude } from '../aptitude/aptitude.model';



export class Survey {
    evaluator: string
    evaluated: string
    role: string
    aptitudes: Aptitude[]

    constructor(){
        this.evaluator = ''
        this.role = ''
        this.evaluated = ''
        this.aptitudes = new Array<Aptitude>()
    }
}


