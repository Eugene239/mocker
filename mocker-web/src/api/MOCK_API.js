import axios from 'axios'

var MOCK_API = {

    getAll(env, uuid, options) {
        return axios.get(env + '/' + uuid, {
            params: {
                size: options.size,
                page: options.page,
                sort: 'created,desc'
            }
        })
    },
    create(env, uuid, method, code, path, body) {
        return axios.post(env + '/' + uuid, body, {
            headers: {
                'Content-Type': 'application/json'
            },
            params: {
                method: method,
                code: code,
                path: path,
            }
        })
    },
    getMock(env, method, path) {
        var url = env + path;
        return axios({
            method: method,
            url: url
        })
    }
}

export default MOCK_API