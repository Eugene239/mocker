import axios from 'axios'

var MOCK_API = {

    getAll(env, uuid, options) {
        return axios.get(env + '/' + uuid, {
            params:{
                size: options.size,
                page: options.page
            }
        })
    }
}

export default MOCK_API