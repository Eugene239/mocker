<template>
    <div>
        <div class="md-card" style="margin-bottom: 2rem; padding: 0.5rem 0.5rem 1rem;">
            <h1 class="md-headline ">Simple Mocker for REST API</h1>
            <div class="md-body-1">
                Use it when backend is not ready<br>
                Mock necessary methods and set Mocker as backend
            </div>
        </div>
        <div class="md-card " style="margin-bottom: 2rem; padding: 0.5rem 0.5rem 1rem;">
            <h1 class="md-headline ">
                <router-link to="create" style="padding-right: 0.5rem">Create new mocks</router-link>
                <md-icon class="md-primary">create</md-icon>
            </h1>
            <div class="md-body-1">
                Create new mocks via site page or any http client<br>
                <pre><code>curl --location --request POST 'https://mocker.badsoft.ml/a79c4bd8-d0e4-11ea-87d0-0242ac130003?path=/api/post&code=200&method=POST' \
--header 'Content-Type: application/json' \
--data-raw '{
"hello": "world"
}'</code></pre>
                For update use same method
            </div>
        </div>
        <div class="md-card" style="margin-bottom: 2rem; padding: 0.5rem 0.5rem 1rem;">
            <h1 class="md-headline ">
                <router-link to="list" style="padding-right: 0.5rem">Check mock in list</router-link>
                <md-icon class="md-primary">list</md-icon>
            </h1>
            <div class="md-body-1">
                Check mock exist on list page
            </div>
        </div>
        <div class="md-card" style="margin-bottom: 2rem; padding: 0.5rem 0.5rem 1rem;">
            <h1 class="md-headline ">
                Source code and dedicate use
            </h1>
            <div class="md-body-1">
                <div>Check github page</div>
                <div class="md-list-item" style="display: flex; align-items: center; margin-top: 1rem">
                    <a href="https://github.com/Eugene239/mocker" style="color: white">
                        <md-avatar class="md-small" style="background-color: white; margin-right: 32px"><img
                                style="width: 20px" src="github.svg"/></md-avatar>
                        Github
                    </a>

                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Info",
    }
</script>

<style scoped>

</style>