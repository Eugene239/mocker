<template>
    <md-dialog v-if="mock" :md-active.sync="show">
        <md-dialog-title class="mocker__dialog">
            <div class="mocker__dialog-title">
                <span :style="mockStyle()">{{mock.method}}</span>
                <span style="padding-left: 1rem">{{mock.code}}</span>
            </div>
            <div class="mocker__dialog-title"> {{mock.path}}</div>
        </md-dialog-title>
        <md-divider></md-divider>
        <div v-if="loading">
            <md-progress-bar class="md-accent" md-mode="indeterminate"></md-progress-bar>
        </div>
        <div class=" mocker__dialog-body">
            <div class="md-title" style="margin-bottom: 1rem">Body</div>
            <md-field>
                <md-textarea v-model="mock.body" required  class="mocker__dialog-response-body" disabled></md-textarea>
            </md-field>
        </div>
        <md-dialog-actions>
            <md-button class="md-primary" @click="show = false">Close</md-button>
        </md-dialog-actions>
    </md-dialog>
</template>

<script>
    import MOCK_API from '@/api/MOCK_API'

    export default {
        name: "MockDialog",
        props: ['mock'],
        watch: {
            mock: function (newOne, oldOne) {
                console.log('[MockDialog](watch: mock)', newOne, oldOne);
                if (newOne) {
                    this.show = true;
                    this.loadMock();
                }
            }

            //     (newOne, oldOne) => {

            //     if (newOne) {
            //         this.show = true;
            //         this.loadMock();
            //     }
            // }
        },
        data: () => ({
            // mock: {
            //     method: "POST",
            //     code: 200,
            //     path: "/api/post",
            //     body: null
            // },
            env: process.env.VUE_APP_BACKEND,
            loading: false,
            show: false
            //showDialog: true
        }),
        mounted() {
            //  this.loadMock();
        },
        methods: {
            mockStyle() {
                return 'color: ' + this.$store.state.colors[this.mock.method];
            },
            loadMock() {
                if (this.mock) {
                    console.log('loadMock');
                    this.loading = true;
                    MOCK_API.getMock(this.env, this.mock.method, this.mock.path).then(
                        response => {
                            this.mock.body = JSON.stringify(response.data, null, 2);
                            this.loading = false;
                        },
                        error => {
                            this.mock.body = JSON.stringify(error.response.data, null, 2);
                            this.loading = false;
                        }
                    )
                }
            },
            onClose() {
                console.log('on close');
            }
        }
    }
</script>
<style>
    .mocker__dialog-body .mocker__dialog-response-body{
        height: 100%;
        max-height: 100%;
    }
</style>