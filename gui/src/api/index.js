import spinner from "../lib/Spin";

const apiPath = "/api/resolver";

function apiUrl(path) {
    return apiPath + path;
}

let started = 0;
let ended = 0;

function validateResponse(showErrorDialog) {
    return res => {
        ++ended;
        if (started <= ended) {
            spinner.stop();
        }
        if (!res.ok) {
            started = ended = 0;
            spinner.stop();

            if (res.type === "opaqueredirect") {
                setTimeout(() => window.location.reload(true), 100);
                return res;
            }
            const error = new Error(res.statusText);
            error.response = res;

            if (showErrorDialog) {
                setTimeout(() => {
                    throw error;
                }, 250);
            }
            throw error;
        }
        return res;

    };
}

function validFetch(path, options, headers = {}, showErrorDialog = true) {
    const authorization_header = localStorage.getItem("authorization_header");
    const contentHeaders = {
        "Accept": "application/json",
        "Content-Type": "application/json",
        "Authorization": authorization_header,
        ...headers
    };
    const fetchOptions = Object.assign({}, {headers: contentHeaders}, options, {
        credentials: "same-origin",
        redirect: "manual",
    });
    spinner.start();
    ++started;

    const targetUrl = apiUrl(path);
    return fetch(targetUrl, fetchOptions).then(validateResponse(showErrorDialog))
}

function fetchJson(path, options = {}, headers = {}, showErrorDialog = true, result = true) {
    return validFetch(path, options, headers, showErrorDialog)
        .then(res => result ? res.json() : {});
}

function postPutJson(path, body, method, result = true) {
    return fetchJson(path, {method: method, body: JSON.stringify(body)}, {}, true, result);
}

export function researcherById(id) {
    return fetchJson(`researchers/${id}`)
}

export function search(query) {
    return fetchJson(`find/researchers?q=${query}`);
}

export function reportError(error) {
    return postPutJson("user/error", error, "post");
}

export function me() {
    return fetchJson("users/me");
}
