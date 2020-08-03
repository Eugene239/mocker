import axios from 'axios'

var MOCK_API = {

    getAll(env, uuid, options) {
        return axios.get(env + '/' + uuid, {
            params:{
                size: options.size,
                page: options.page,
                sort: 'created,desc'
            }
        })
    },
    create(env, uuid, method, code, path, body){
        return axios.post(env + '/' + uuid, body,{
            headers: {
                'Content-Type': 'application/json'
            },
            params:{
                method: method,
                code: code,
                path: path,
            }
        })
    }
}

export default MOCK_API