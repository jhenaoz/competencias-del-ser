import { IEmployee } from '../employee/employee.model';

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

export class Aptitude {
    aptitudeId: string
    observation: string
    behaviors: Behavior[]

    constructor(){
        this.aptitudeId = ''
        this.observation = ''
        this.behaviors = new Array<Behavior>()
    }

}

export class Behavior {
    behaviorId: string
    score: number

    constructor(){
        this.behaviorId = ''
        this.score = 0
    }
}
