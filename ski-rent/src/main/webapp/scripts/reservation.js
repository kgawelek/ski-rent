function chooseSelected() {
    let href = 'reservation.html#';

    let checkboxes = document.querySelectorAll('input.equipment:checked');
    if (checkboxes.length === 0) {
        return;
    }

    checkboxes.forEach(checkbox => {
        console.debug('Selected item: ', checkbox.id);
        href += checkbox.id + '&';
    });

    href = href.slice(0, -1); // remove last '&' or '#'

    location.href = href;
}

/**
 * @param {string} id
 * @param {Map<string, Equipment>} equipmentMap
 * @returns {Promise<any>}
 */
function fetchByIdAndAddToMap(id, equipmentMap) {
    return fetch(`http://localhost:8080/api/equipment/${id}`)
        .then(res => res.json())
        .then(equipment => { equipmentMap.set(id, equipment); });
}

function readHash() {
    if (window.location.hash) {
        // retrieve params:
        /** @type {Map<string, Equipment>} */
        const equipmentMap = new Map();
        /** @type {Promise[]} */
        let promiseArray = [];

        if (window.localStorage.getItem('ids')) { // add existing ids
            window.localStorage.getItem('ids').split('&').forEach(id => {
                promiseArray.push(
                    fetchByIdAndAddToMap(id, equipmentMap)
                );
            });
        }


        // wait for all fetches to complete:
        Promise.all(promiseArray).then(() => {
            window.location.hash.slice(1).split('&').forEach(id => { // add new ids
                !equipmentMap.has(id) && promiseArray.push(
                    fetchByIdAndAddToMap(id, equipmentMap)
                );
            });

            // wait again for all fetches to complete:
            Promise.all(promiseArray)
                .then(() => {
                    window.localStorage.setItem('ids', Array.from(equipmentMap).map(([id, equipment]) => id).join('&'));

                    // preparation for table:
                    /** @type {Map<string, string>} */
                    let categoryTitlePairsMap = new Map([
                        ['SKI_POLES', 'Kijki narciarskie'],
                        ['SKI', 'Narty'],
                        ['SNOWBOARD', 'Deski snowboardowe'],
                        ['GOGGLES', 'Gogle narciarskie i snowboardowe'],
                        ['SKI_BOOTS', 'Buty narciarskie'],
                        ['SNOWBOARDS_BOOTS', 'Buty snowboardowe'],
                        ['HELMET', 'Kaski'],
                        ['PANTS', 'Spodnie'],
                        ['JACKET', 'Kurtki'],
                        ['GLOVES', 'Rękawice'],
                        ['BALACLAVAS', 'Kominiarki'],
                        ['CAP', 'Czapki'],
                        ['SCARF', 'Szaliki']
                    ]);

                    // table:
                    let table = document.createElement('table');    // <table></table>
                    let thead = document.createElement('thead');    // <thead></thead>
                    thead.innerHTML = `                                     
                <th>Nazwa</th>
                <th>Producent</th>
                <th>Rozmiar</th>
                <th>Kategoria</th>`;                            // <thead>...</thead>
                    let tbody = document.createElement('tbody');    // <tbody></tbody>

                    // append elements:
                    equipmentMap.forEach(equipment => {
                        let row = document.createElement('tr');
                        row.innerHTML = `
                    <td>${equipment.name}</td>
                    <td>${equipment.manufacturer}</td>
                    <td>${equipment.size}</td>
                    <td>${categoryTitlePairsMap.get(equipment.category)}</td>`;
                        tbody.append(row);
                    });
                    table.append(thead, tbody);                             // <table><thead>...</thead><tbody>...</tbody></table>

                    let mainDiv = document.querySelector(`div#reservation_items_table`);
                    mainDiv.append(table);
                });
        });
    }

    function acceptReservation() {


    }
}