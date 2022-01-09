/**
 * @typedef {Object} Equipment
 * @property {number} id
 * @property {string} name
 * @property {string} manufacturer
 * @property {string} size
 * @property {string} category
 */


/**
 * Funkcja pomocnicza.
 * @param {Equipment} equipment
 */
function makeEquipmentRow(equipment) {
    let row = document.createElement('tr');
    row.innerHTML = `
                    <td>${equipment.name}</td>
                    <td>${equipment.manufacturer}</td>
                    <td>${equipment.size}</td>
                    <td>
                        <input type="checkbox" id="${equipment.id}" name="${equipment.id}" style="font-size: 2em" />
                    </td>`;
    return row;
}

/**
 * Funkcja tworzy listę sprzętu/odzieży oraz dodaje ją do strony, opcjonalnie dodając pasek z filtrami.
 * @param {Equipment[]} data
 * @param {('gear'|'clothes')} equipmentType
 * @param {boolean} generateFilters
 */
function createEquipmentTable(data, equipmentType, generateFilters = true) {
    console.debug('response:', data);
    window.equipmentType = equipmentType; // saving for later

    /** @type {Map<string, string>} */
    let categoryTitlePairsMap;
    switch (equipmentType) {
        case "gear":
            categoryTitlePairsMap = new Map([
                ['SKI_POLES', 'Kijki narciarskie'],
                ['SKI', 'Narty'],
                ['SNOWBOARD', 'Deski snowboardowe'],
                ['GOGGLES', 'Gogle narciarskie i snowboardowe'],
                ['SKI_BOOTS', 'Buty narciarskie'],
                ['SNOWBOARDS_BOOTS', 'Buty snowboardowe'],
                ['HELMET', 'Kaski']
            ]);
            break;
        case "clothes":
            categoryTitlePairsMap = new Map([
                ['PANTS', 'Spodnie'],
                ['JACKET', 'Kurtki'],
                ['GLOVES', 'Rękawice'],
                ['BALACLAVAS', 'Kominiarki'],
                ['CAP', 'Czapki'],
                ['SCARF', 'Szaliki']
            ]);
            break;
        default:
            break;
    }

    // tworzenie div z nagłówkami i tabelami:
    /**
     * @type {Map<string, HTMLDivElement>}
     */
    let categoryTitleDivs = new Map();

    categoryTitlePairsMap.forEach((title, category) => {
        let div = document.createElement('div');        // <div></div>
        div.id = category.toLowerCase();                        // <div id="ski_poles"></div>

        let a = document.createElement('a');            // <a></a>
        a.id = category.toLowerCase();                          // <a id="ski_poles"></a>
        const firstBr = document.createElement('br');   // <br>
        const secondBr = document.createElement('br');  // <br>
        let h3 = document.createElement('h3');          // <h3></h3>
        h3.innerText = title.toUpperCase();                     // <h3>KIJKI NARCIARSKIE</h3>

        let table = document.createElement('table');    // <table></table>
        let thead = document.createElement('thead');    // <thead></thead>
        thead.innerHTML = `                                     
                <th>Nazwa</th>
                <th>Producent</th>
                <th>Rozmiar</th>
                <th>Wybierz</th>`;                                  // <thead>...</thead>
        let tbody = document.createElement('tbody');    // <tbody></tbody>
        table.append(thead, tbody);                             // <table><thead>...</thead><tbody></tbody></table>

        div.append(a, firstBr, secondBr, h3, table);            // dodaj elementy do div
        categoryTitleDivs.set(category, div);                   // dodaj div do mapy
    });

    // ładowanie elementów do div:
    data.forEach(equipment => {
        let div = categoryTitleDivs.get(equipment.category);
        if (div)
            div.querySelector('tbody').appendChild(makeEquipmentRow(equipment));
        else
            console.warn('Unknown equipment category', equipment.category);
    });

    // filtry:
    if(generateFilters) {
        let sizesSet = new Set();
        let manufacturersSet = new Set();
        let categoriesSet = new Set();

        // ładowanie elementów do filtrów:
        data.forEach(equipment => {
            let div = categoryTitleDivs.get(equipment.category);
            if (div) {
                sizesSet.add(equipment.size);
                manufacturersSet.add(equipment.manufacturer);
                categoriesSet.add(equipment.category);
            }
        });

        let divFilters = document.querySelector('div#filters');

        const makeDivWithCheckboxes = (id, name, set, translate = false) => {
            let newDiv = document.createElement('div');
            newDiv.id = id;
            newDiv.innerHTML = `<h4>${name}:</h4>`;
            // append checkboxes:
            set.forEach(value => {
                let div = document.createElement('div');
                div.innerHTML = `<label for="${value}"  style="word-wrap:break-word">
                    <input id="${value}"  type="checkbox" value="${value}" /> ${translate ? categoryTitlePairsMap.get(value) : value}
                 </label>`;

                newDiv.appendChild(div);
            });
            return newDiv;
        }

        let filterButton = document.createElement('input');
        filterButton.type = 'button';
        filterButton.value = 'Zastosuj filtry';
        filterButton.className = 'center';
        filterButton.addEventListener("click", applyFilters);

        divFilters.append(
            makeDivWithCheckboxes('sizes', 'Rozmiary', sizesSet),
            makeDivWithCheckboxes('manufacturers', 'Producenci', manufacturersSet),
            makeDivWithCheckboxes('categories', 'Kategorie', categoriesSet, true),
            filterButton);
    }

    // dodawanie sekcji do głównego diva:
    let mainDiv = document.querySelector(`div#${equipmentType}_table`);
    mainDiv.innerHTML = ''; //  czyszczenie aktualnej zawartości
    categoryTitleDivs.forEach(div => {
        div.querySelector('tbody tr') && mainDiv.appendChild(div); // dodaj tylko wtedy, kiedy jest jakiś sprzęt o tej kategorii
    });
}

/**
 * Funkcja wywoływana po załadowaniu się body HTML. Pobiera dane z bazy i tworzy listę sprzętu/odzieży oraz dodaje ją do strony.
 * @param {('gear'|'clothes')} equipmentType
 * @param {boolean} generateFilters
 */
function fetchAndCreateEquipmentTable(equipmentType, generateFilters = true) {
    fetch(`api/equipment/${equipmentType}`)
        .then(response => response.json())
        .then(data => {
            createEquipmentTable(data, equipmentType, generateFilters);
            if(window.location.hash) {
                setTimeout(() => {
                    const divToScroll = document.querySelector(`div${window.location.hash}`);
                    divToScroll && divToScroll.scrollIntoView();
                }, 10);
            }
        })
        .catch(err => {
            console.error('Table creation error:', err);
        });
}

/**
 * Funkcja wywoływana po naciśnięciu przycisku 'zastosuj filtry'.
 */
function applyFilters() {
    let requestBody = {};
    requestBody.sizes = [];
    requestBody.manufacturers = [];
    requestBody.categories = [];

    const appendToRequestBody = (id, array) => {
        document.querySelector(`div#${id}`)
            .querySelectorAll('input')
            .forEach(checkbox => {
                    checkbox.checked && array.push(checkbox.value);
                }
            );
    }

    appendToRequestBody('sizes', requestBody.sizes);
    appendToRequestBody('manufacturers', requestBody.manufacturers);
    appendToRequestBody('categories', requestBody.categories);

    fetch(`api/equipment/filter/${window.equipmentType}`,
    {
            method: 'POST',
            body: JSON.stringify(requestBody),
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
                Pragma: 'no-cache'
            }
        })
        .then(response => response.json())
        .then(data => {
            createEquipmentTable(data, window.equipmentType, false);
        })
        .catch(err => {
            console.error('Table creation with filters error:', err);
        });
}