import { Behavior } from './behavior.model';

export class Aptitude {
    aptitudeId: string;
    observation: string;
    behaviors: Behavior[];

    constructor() {
        this.aptitudeId = '';
        this.observation = '';
        this.behaviors = new Array<Behavior>();
    }
}

