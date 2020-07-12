import {RECEIVE_LIST} from './mutation-type'

export default{
    [RECEIVE_LIST](state, {list_data}){
        state.listTmp = list_data
        console.log(state.listTmp)
    }
}