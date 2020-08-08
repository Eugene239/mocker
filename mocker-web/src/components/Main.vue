<template>
    <div class="page-container">
        <md-app>
            <md-app-toolbar class="md-primary">
                <md-button class="md-icon-button" @click="menuVisible = !menuVisible">
                    <md-icon>menu</md-icon>
                </md-button>
                <span class="md-title">Mocker</span>

                <md-button style="margin-left: auto" @click="refresh()">
                    <md-icon>refresh</md-icon>
                </md-button>
            </md-app-toolbar>

            <md-app-drawer :md-active.sync="menuVisible" >
                <md-list>
                    <md-subheader>Mocker</md-subheader>
                    <md-list-item @click="menuVisible=false" class="navigation-link" to="/info">
                        <md-icon>info</md-icon>
                        <span class="md-list-item-text "> Info </span>
                    </md-list-item>

                    <md-list-item @click="menuVisible=false" class="navigation-link" to="/create">
                        <md-icon>create</md-icon>
                        <span class="md-list-item-text "> Create</span>
                    </md-list-item>

                    <md-list-item @click="menuVisible=false" class="navigation-link" to="/list">
                        <md-icon>list</md-icon>
                        <span class="md-list-item-text ">List</span>
                    </md-list-item>
                    <md-divider style="margin-top: 1rem;"></md-divider>
                    <md-subheader>Source</md-subheader>
                    <md-list-item @click="menuVisible=false" class="navigation-link" >
                        <md-avatar  class="md-small" style="background-color: white; margin-right: 32px">
                            <img  style="width: 20px" src="github.svg"/>
                        </md-avatar>
                        <span class="md-list-item-text">
                            <a href="https://github.com/Eugene239/mocker" style="color: white" >Github</a>
                        </span>
                    </md-list-item>
                </md-list>
            </md-app-drawer>

            <md-app-content class="mocker__page" >
                <div v-if="loading" class="mocker__loading">
                    <md-progress-bar class="md-accent" md-mode="indeterminate"></md-progress-bar>
                </div>
                <router-view class="mocker__router-content" v-if="this.$store.state.uuid"></router-view>
            </md-app-content>
        </md-app>
    </div>
</template>

<style lang="scss" scoped>
    .page-container {
        height: 100%;
    }

    .md-app {
        border: 1px solid rgba(#000, .12);
        height: 100%;
    }

    .md-drawer {
        width: 230px;
        max-width: calc(100vw - 125px);
        display: flex;
        flex-direction: column;
    }
    .md-drawer .md-list{
        flex: 1;
    }

    .md-content.md-app-content {
        background-color: #212121;
    }

    .md-app-container {
        height: 100%;
    }
    .navigation-link .md-list-item-text {
        color: white !important;
    }
</style>

<script>
    import UUID_API from '@/api/UUID_API'

    export default {
        name: 'Main',
        mounted() {
            this.loadInfo();
        },
        computed: {
            loading() {
                return this.$store.state.loading;
            }
        },
        data: () => ({
            menuVisible: false,
            env: process.env.VUE_APP_BACKEND,
        }),
        methods: {
            loadInfo: function () {
                // fill uuid
                if (!this.$store.state.uuid) {
                    this.$store.commit('setLoading', true);
                    UUID_API.getUUID(this.env).then(
                        response => {
                            console.log(response.data.uuid);
                            this.$store.commit('setUUID', response.data.uuid);
                            this.$store.commit('setLoading', false);
                        },
                        error => {
                            this.$store.commit('setLoading', false);
                            console.error(error);
                        }
                    )
                }
            },
            refresh: () => {
                console.log('refresh');
                window.location.reload();
            }
        }
    }
</script>