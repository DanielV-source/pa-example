const getModuleState = state => state.inscription;

export const getInscriptionId = state =>
    getModuleState(state).inscriptionId;

export const getPickUpDorsal = state =>
    getModuleState(state).deliverDorsalResult;

export const getLastInscription = state =>
    getModuleState(state).lastInscription;

export const getInscriptionSearch = state =>
    getModuleState(state).inscriptionSearch;

export const getSportEventName = (inscriptions, id) => {
    if(!inscriptions) {
        return '';
    }
    const inscription = inscriptions.result.items.find(inscription => inscription.inscriptionId === id);

    if (!inscription) {
        return '';
    }
    return inscription.sportEventName;
}