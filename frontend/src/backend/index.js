import {init} from './appFetch';
import * as userService from './userService';
import * as catalogService from './catalogService';
import * as inscriptionService from './inscriptionService';

export {default as NetworkError} from "./NetworkError";

export default {init, userService, catalogService, inscriptionService};
