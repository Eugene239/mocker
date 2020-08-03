<template>
    <div>
        <form novalidate class="md-layout" @submit.prevent="validateMock" style="margin-top: 2rem">
            <md-card class="md-layout-item md-size-50 md-small-size-100" style="margin: auto;">
                <md-card-header style="display: flex">
                    <div class="md-title">Create Mock</div>
                </md-card-header>
                <md-divider></md-divider>
                <md-card-content>
                    <div class="md-layout md-gutter">
                        <div class="md-layout-item md-small-size-100">
                            <md-field>
                                <label for="method">Method</label>
                                <md-select v-model="form.method" name="method" id="method" :disabled="loading">
                                    <md-option v-for="method in methods" :key="method" :value="method">{{method}}
                                    </md-option>
                                </md-select>
                                <md-icon>http</md-icon>
                            </md-field>
                        </div>
                        <div class="md-layout-item md-small-size-100">
                            <md-field :class="getValidationClass('code')">
                                <label for="code">Code</label>
                                <md-input type="number" id="code" name="code" autocomplete="code" v-model="form.code"
                                          :disabled="loading"/>
                                <span class="md-error" v-if="!$v.form.code.required">Code is required</span>
                                <span class="md-error" v-if="!$v.form.code.isCodeValid">Invalid code</span>
                                <md-icon>http</md-icon>
                            </md-field>
                        </div>
                    </div>

                    <md-field :class="getValidationClass('path')">
                        <label for="path">Path</label>
                        <md-input type="text" v-model="form.path" :disabled="loading"/>
                        <span class="md-error" v-if="!$v.form.path.required">The Path is required</span>
                        <span class="md-error" v-if="!$v.form.path.isPathValid">Invalid path, use /\S+/\S+ paths</span>
                        <span class="md-error" v-else-if="!$v.form.path">Invalid path</span>
                        <md-icon>public</md-icon>
                    </md-field>

                    <md-field :class="getValidationClass('response')">
                        <label>Response</label>
                        <md-textarea v-model="form.response" required style="max-height: 100%"></md-textarea>
                        <span class="md-helper-text">JSON mock response</span>
                        <span class="md-error" v-if="!$v.form.response.isJsonValid">Response json is invalid</span>
                        <span class="md-error" v-else-if="!$v.form.response">There is an error</span>
                        <md-icon>description</md-icon>
                    </md-field>

                </md-card-content>

                <md-card-actions>
                    <md-button type="submit" class="md-primary" :disabled="loading">Create</md-button>
                </md-card-actions>
            </md-card>

            <md-snackbar :md-active.sync="mockSaved">The mock {{ lastMock }} was saved with success!</md-snackbar>
        </form>
    </div>
</template>

<script>
    import MOCK_API from '@/api/MOCK_API'
    import {validationMixin} from 'vuelidate'
    import {
        required,
        minLength,
        maxLength
    } from 'vuelidate/lib/validators'


    const isPathValid = (value) => {
        if (value == null || value.trim().length === 0) return false;
        try {
            return value.match(/\/\S+\/\S+/gm) != null;
        } catch (e) {
            return false;
        }
    }

    const isJsonValid = (value) => {
        if (value == null || value.trim().length ===0) return true;
        try {
            JSON.parse(value);
            return true;
        }catch (e) {
            return false;
        }
    }

    const isCodeValid = (value) => {
        var codes = [];
        for (let i = 100; i <= 103; i++) {
            codes.push(i);
        }
        for (let i = 200; i <= 208; i++) {
            codes.push(i);
        }
        for (let i = 300; i <= 308; i++) {
            codes.push(i);
        }
        for (let i = 400; i <= 431; i++) {
            codes.push(i);
        }
        for (let i = 449; i <= 451; i++) {
            codes.push(i);
        }
        codes.push('499');
        for (let i = 500; i <= 511; i++) {
            codes.push(i);
        }
        for (let i = 520; i <= 526; i++) {
            codes.push(i);
        }
        return codes.indexOf(value) !== -1;
    }


    export default {
        name: 'Create',
        mixins: [validationMixin],
        watch: {
            loading: function (newV, oldV) {
                console.log('[WATCH] loading', newV, oldV);
                this.$store.commit('setLoading', newV);
            }
        },
        mounted() {
            this.methods = this.$store.state.methods;
            this.form.method = this.methods[0];
        },
        data: () => ({
            form: {
                method: null,
                code: 200,
                path: '/',
                response: null
            },
            env: process.env.VUE_APP_BACKEND,
            methods: [],
            mockSaved: false,
            loading: false,
            lastMock: null
        }),
        validations: {
            form: {
                code: {
                    required,
                    maxLength: maxLength(3),
                    minLength: minLength(3),
                    isCodeValid
                },
                path: {
                    required,
                    isPathValid
                },
                response: {
                    isJsonValid
                }
            }
        },


        methods: {
            getValidationClass(fieldName) {
                const field = this.$v.form[fieldName]

                if (field) {
                    return {
                        'md-invalid': field.$invalid && field.$dirty
                    }
                }
            },
            clearForm() {
                this.$v.$reset()
                this.form.path = '/'
                this.form.response = null
            },
            saveMock() {
                this.loading = true;
                var json = this.form.response? JSON.parse(this.form.response) : null
                MOCK_API.create(this.env, this.$store.state.uuid, this.form.method, this.form.code, this.form.path, json).then(
                    response => {
                        console.log(response); //todo process
                        this.loading = false;
                        this.lastMock = `${this.form.method} ${this.form.path} ${this.form.code}`
                        this.mockSaved = true;
                        //this.clearForm(); todo remove comment
                    },
                    error => {
                        console.error(error); //todo process error
                        this.loading = false
                    }
                )
            },
            validateMock() {
                this.$v.$touch()

                if (!this.$v.$invalid) {
                    this.saveMock()
                }
            },
        }
    }
</script>
