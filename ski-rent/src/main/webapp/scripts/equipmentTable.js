/**
 * @typedef {Object} Equipment
 * @property {number} id
 * @property {string} name
 * @property {string} manufacturer
 * @property {string} size
 * @property {string} category
 */


/**
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
 * Funkcja wywoływana po załadowaniu się body HTML. Pobiera z bazy i tworzy listę sprzętu/odzieży oraz dodaje ją do strony.
 * @param {('gear'|'clothes')} equipmentType
 * @param {boolean} generateFilters
 */
function createEquipmentTable(equipmentType, generateFilters = true) {
    fetch(`api/equipment/${equipmentType}`)
        .then(response => response.json())
        .then(data => {
            console.log(data);

            let categoryTitlePairs = [];
            switch (equipmentType) {
                case "gear":
                    categoryTitlePairs = [
                        { category: 'SKI_POLES', title: 'KIJKI NARCIARSKIE' },
                        { category: 'SKI', title: 'NARTY' },
                        { category: 'SNOWBOARD', title: 'DESKI SNOWBOARDOWE' },
                        { category: 'GOGGLES', title: 'GOGLE NARCIARSKIE I SNOWBOARDOWE' },
                        { category: 'SKI_BOOTS', title: 'BUTY NARCIARSKIE' },
                        { category: 'SNOWBOARDS_BOOTS', title: 'BUTY' },
                        { category: 'HELMET', title: 'KASKI' }
                    ];
                    break;
                case "clothes":
                    categoryTitlePairs = [
                        { category: 'PANTS', title: 'SPODNIE' },
                        { category: 'JACKET', title: 'KURTKI' },
                        { category: 'GLOVES', title: 'RĘKAWICE' },
                        { category: 'BALACLAVAS', title: 'KOMINIARKI' },
                        { category: 'CAP', title: 'CZAPKI' },
                        { category: 'SCARF', title: 'SZALIKI' }
                    ];
                    break;
                default:
                    break;
            }

            // tworzenie div z nagłówkami i tabelami:
            /**
             * @type {Map<string, HTMLDivElement>}
             */
            let categoryTitleDivs = new Map();
            categoryTitlePairs.forEach(pair => {
                let div = document.createElement('div');        // <div></div>
                div.id = pair.category.toLowerCase();                   // <div id="ski_poles"></div>

                let a = document.createElement('a');            // <a></a>
                a.id = pair.category.toLowerCase();                     // <a id="ski_poles"></a>
                const firstBr = document.createElement('br');   // <br>
                const secondBr = document.createElement('br');  // <br>
                let h3 = document.createElement('h3');          // <h3></h3>
                h3.innerText = pair.title;                              // <h3>KIJKI NARCIARSKIE</h3>

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
                categoryTitleDivs.set(pair.category, div);              // dodaj div do mapy
            });

            // filtry:
            let sizesSet = new Set();
            let manufacturersSet = new Set();
            let categoriesSet = new Set();

            // ładowanie elementów do div i filtrów:
            data.forEach(equipment => {
                let div = categoryTitleDivs.get(equipment.category);
                if (div) {
                    div.querySelector('tbody').appendChild(makeEquipmentRow(equipment));

                    sizesSet.add(equipment.size);
                    manufacturersSet.add(equipment.manufacturer);
                    categoriesSet.add(equipment.category);
                }
                else
                    console.warn('Unknown equipment category', equipment.category);
            });

            // filtry:
            let divFilters = document.querySelector('div#filters');

            const appendCheckboxes = (set, parentDiv) => {
                set.forEach(value => {
                    let div = document.createElement('div');
                    div.innerHTML = `<label for="${value}"  style="word-wrap:break-word">
                        <input id="${value}"  type="checkbox" value="${value}" /> ${value}
                     </label>`;

                    parentDiv.appendChild(div);
                });
            }

            let sizesDiv = document.createElement('div');
            sizesDiv.innerHTML = '<h4>Rozmiary:</h4>';
            appendCheckboxes(sizesSet, sizesDiv)

            let manufacturersDiv = document.createElement('div');
            manufacturersDiv.innerHTML = '<h4>Producenci:</h4>';
            appendCheckboxes(manufacturersSet, manufacturersDiv);

            let categoriesDiv = document.createElement('div');
            categoriesDiv.innerHTML = '<h4>Kategorie:</h4>';
            appendCheckboxes(categoriesSet, categoriesDiv);

            let filterButton = document.createElement('input');
            filterButton.type = 'button';
            filterButton.value = 'Zastosuj filtry';
            filterButton.addEventListener("click", applyFilters);

            divFilters.append(sizesDiv, manufacturersDiv, categoriesDiv, filterButton);

            // dodawanie sekcji do głównego diva:
            let mainDiv = document.querySelector(`div#${equipmentType}_table`);
            categoryTitleDivs.forEach(div => {
                div.querySelector('tbody tr') && mainDiv.appendChild(div); // dodaj tylko wtedy, kiedy jest jakiś sprzęt o tej kategorii
            });
        })
        .catch(err => {
            console.error('Table creation error:', err);
        });
}

function applyFilters() {
    console.log("klik");
}