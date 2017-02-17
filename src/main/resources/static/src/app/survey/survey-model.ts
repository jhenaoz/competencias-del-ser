import { IEmployee } from '../employee/employee.model';

export interface ISurvey {
    evaluator: string
    evaluated: IEmployee[]
    role: string
    aptitudes: IAptitude[]
}

export interface IAptitude {
    aptitudeId: string
    observation: string
    behaviors: IBehavior[]

}

export interface IBehavior {
    behaviorId: string
    score: number
}
