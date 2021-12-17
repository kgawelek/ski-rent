function loadScript(url) {
    return new Promise((resolve, reject) => {
        let script = document.createElement('script');
        script.src = 'scripts/' + url;
        script.async = false;
        script.defer = true;
        script.onload = () => resolve(url);
        script.onerror = () => reject(url);
        document.body.appendChild(script);
    });
}

let scripts = [
    'alerts.js',
    'jquery-3.6.0.min.js',
    'includes.js'
];

// save all Promises as array
let promises = scripts.map((url) => loadScript(url));

Promise.all(promises)
    .then(
        () => console.debug('all scripts loaded')
    )
    .catch(
        (script) => console.warn(script + ' failed to load')
    );