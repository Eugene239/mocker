<template>
    <div>
        <md-table class="md-card md-small-size-100 md-small-p-1 mocker__list" style="padding-bottom: 1rem">
            <md-table-toolbar>
                <h1 class="md-title">Mock List</h1>
                <div style="display: flex">
                    <div class="md-subheading" style="margin: auto">
                        {{(page+1)}} - {{totalPages}}
                    </div>
                    <md-button class="md-icon-button" :disabled="first" @click="move(page-1)">
                        <md-icon>keyboard_arrow_left</md-icon>
                    </md-button>
                    <md-button class="md-icon-button " :disabled="last" @click="move(page+1)">
                        <md-icon>keyboard_arrow_right</md-icon>
                    </md-button>
                </div>
            </md-table-toolbar>

            <md-table-row>
                <md-table-head>METHOD</md-table-head>
                <md-table-head>PATH</md-table-head>
                <md-table-head class="md-small-hide">CODE</md-table-head>
                <md-table-head class="md-small-hide">CREATED</md-table-head>
            </md-table-row>

            <md-table-row v-for="mock in mocks" v-bind:key="hashf(mock)" style="cursor: pointer"
                          @click="showMock(mock)">
                <md-table-cell :style="mockStyle(mock)">{{mock.method}}</md-table-cell>
                <md-table-cell class="mocker-path">{{mock.path}}</md-table-cell>
                <md-table-cell class="md-small-hide">{{mock.code}}</md-table-cell>
                <md-table-cell class="md-small-hide">{{formatDate(mock.created)}}</md-table-cell>
                <!--                <md-table-cell>-->
                <!--                    <md-icon>trash</md-icon>-->
                <!--                </md-table-cell>-->
            </md-table-row>
            <md-button class="md-icon-button md-raised md-fab md-accent mocker__fab" to="/create">
                <md-icon>add</md-icon>
            </md-button>
        </md-table>
        <!--        <md-button style="position: absolute; right: 0; bottom: 0; z-index: 0">-->


        <!--        </md-button>-->
        <md-dialog :md-active.sync="showDialog" v-if="selectedMock">
            <md-dialog-title class="mocker__dialog" >
                <div class="mocker__dialog-title">
                    <span :style="mockStyle(selectedMock)">{{selectedMock.method}}</span>
                    <span style="padding-left: 1rem">{{selectedMock.code}}</span>
                </div>
                <div class="mocker__dialog-title"> {{selectedMock.path}}</div>
            </md-dialog-title>
            <md-divider></md-divider>
            <div class=" mocker__dialog-body">
                <div class="md-title">Body</div>
                <div class="md-title">TBD</div>
            </div>
            <div>{{selectedMock.body}}</div>
            <md-dialog-actions>
                <md-button class="md-primary" @click="showDialog = false">Close</md-button>
            </md-dialog-actions>
        </md-dialog>
    </div>
</template>

<script>
    //todo transform to card for adaptive
    var hash = require('object-hash');

    import MOCK_API from '@/api/MOCK_API'

    export default {
        name: "MockList",
        mounted() {
            this.loadMocks();
        },
        watch: {
            loading: function (newV, oldV) {
                console.log('[WATCH] loading', newV, oldV);
                this.$store.commit('setLoading', newV);
            },
            page: function (newV, oldV) {
                console.log('[WATCH] page', newV, oldV);
                this.loadMocks();
            }
        },
        data: () => ({
            env: process.env.VUE_APP_BACKEND,
            size: 20,
            page: 0,
            totalPages: 1,
            first: true,
            last: true,
            loading: false,
            mocks: [],
            showDialog: false,
            selectedMock: null
        }),
        methods: {
            loadMocks() {
                if (!this.loading) {
                    this.loading = true;
                    MOCK_API.getAll(this.env, this.$store.state.uuid, {
                        size: this.size,
                        page: this.page
                    }).then(
                        response => {
                            console.log(response);
                            this.mocks = response.data.content;
                            this.first = response.data.first;
                            this.last = response.data.last;
                            this.totalPages = response.data.totalPages;
                            this.page = response.data.number;
                            this.loading = false;
                        },
                        error => {
                            console.error(error);
                            this.loading = false;
                        }
                    )
                }
            },
            // pretty: function (value) {
            //     return value
            // },
            mockStyle: function (mock) {
                return 'color: ' + this.$store.state.colors[mock.method];
            },
            move: function (value) {
                if (!this.loading) {
                    console.log('[MOVE]', value);
                    this.page = value;
                }
            },
            formatDate: function (date) {
                if (!date) return '';
                if (date.indexOf('T') === -1) return date;
                return date.split('T')[0];
            },
            hashf(object) {
                return hash(object);
            },
            showMock(mock) {
                console.log(mock);
                this.selectedMock = mock;
                this.showDialog = true;

            }
        }
    }
</script>

<style type="scss">
    .mocker-path .md-table-cell-container {
        text-overflow: ellipsis;
        max-width: 40vw;
        overflow: hidden;
    }

    .md-dialog-container {
        min-width: 50vw;
        max-width: 100vw;
        width: fit-content;
    }



</style>