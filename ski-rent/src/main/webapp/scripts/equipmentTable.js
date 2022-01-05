/**
 * @typedef {Object} Equipment
 * @property {string} name
 * @property {string} manufacturer
 * @property {string} size
 * @property {string} nextCheckUp
 * @property {string} category
 */


/**
 * @param {Equipment} equipment
 */
function makeEquipmentTableEntity(equipment) {
    let table = document.createElement('table');
    table.innerHTML = `
        <tr>
            <td>Nazwa</td>
            <td>${equipment.name}</td>
        </tr>
        <tr>
            <td>Producent</td>
            <td>${equipment.manufacturer}</td>
        </tr>
        <tr>
            <td>Rozmiar</td>
            <td>${equipment.size}</td>
        </tr>`;
    return table;
}

/**
 * Funkcja wywoływana po załadowaniu się body HTML. Pobiera z bazy i tworzy listę sprzętu/odzieży oraz dodaje ją do strony.
 * @param {('gear'|'clothes')} equipmentType
 */
function createEquipmentTable(equipmentType) {
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

                div.append(a, firstBr, secondBr, h3);                   // dodaj elementy do div
                categoryTitleDivs.set(pair.category, div);              // dodaj div do mapy
            });


            // ładowanie elementów do div:
            data.forEach(equipment => {
                let div = categoryTitleDivs.get(equipment.category);
                if (div)
                    div.appendChild(makeEquipmentTableEntity(equipment));
                else
                    console.warn('Unknown equipment category', equipment.category);
            });


            // dodawanie sekcji do głównego diva:
            let mainDiv = document.querySelector(`div#${equipmentType}_table`);
            categoryTitleDivs.forEach(div => {
                div.querySelector('table') && mainDiv.appendChild(div); // dodaj tylko wtedy, kiedy jest jakiś sprzęt o tej kategorii
            });
        })
        .catch(err => {
            console.error('Error:', err);
        });
}