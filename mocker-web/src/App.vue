<template>
    <div id="app" class="app">
        <Main></Main>
    </div>
</template>

<script>

    import Main from './components/Main.vue'

    export default {
        name: 'App',
        //store: store,
        components: {
            Main
        }
    }

</script>

<style type="text/css">
    .app {
        height: 100vh;
        background-color: red;
    }

    pre code {
        background-color: #424242;
        border: 1px solid #999;
        display: block;
        padding: 20px;
        overflow: hidden;
        word-break: break-word;
        white-space: break-spaces;
    }

    .mocker__fab {
        z-index: 0 !important;
        right: -1.5rem !important;
        bottom: -1.5rem !important;
        position: absolute !important;
    }

    .mocker__page {
        position: relative !important;
    }

    .mocker__dialog {
        display: flex;
        flex-wrap: wrap;
    }

    .mocker__dialog-title {
        flex: 1;
        padding: 0.5rem;
        word-break: break-word;
    }

    .mocker__dialog-body {
        padding: 1rem;
    }

    .mocker__loading {
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        margin: 0;
        padding: 0;
        overflow: hidden
    }

    @media screen and (max-width: 600px) {
        .md-dialog-container {
            width: 100% !important;
        }

        .mocker__page {
            padding-left: 0 !important;
            padding-right: 0 !important;
            margin: 0 !important;

        }

        .mocker__list {
            padding: 0 !important;
            margin: 0 !important;
        }

        .mocker__page {
            position: inherit !important;
        }

        .mocker__dialog-title {
            flex: 1 0 100%;
        }

        .mocker__fab {
            /*todo add show*/
            display: none !important;
        }
        .md-app-content{
            padding: 0;
        }
        .mocker__form{
            margin: 0!important;
        }
        .mocker__loading {
            position: relative;
        }


    }

</style>
