import axios from 'axios'

var UUID_API = {

    getUUID(env) {
        return axios.get(env+'/uuid')
    }
}

export default UUID_API