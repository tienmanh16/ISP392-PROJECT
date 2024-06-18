// Get unique values for the desired columns
function getUniqueValuesFromColumn() {
    const uniqueColValuesDict = {};

    document.querySelectorAll(".table-filter").forEach((filter) => {
        const colIndex = filter.parentElement.getAttribute("col-index");
        const rows = document.querySelectorAll("#emp-table > tbody > tr");

        rows.forEach((row) => {
            const cellValue = row.querySelector(`td:nth-child(${colIndex})`).innerHTML.trim();

            if (!uniqueColValuesDict[colIndex]) {
                uniqueColValuesDict[colIndex] = [];
            }

            if (!uniqueColValuesDict[colIndex].includes(cellValue)) {
                uniqueColValuesDict[colIndex].push(cellValue);
            }
        });
    });

    updateSelectOptions(uniqueColValuesDict);
}

// Add <option> tags to the desired columns based on the unique values
function updateSelectOptions(uniqueColValuesDict) {
    document.querySelectorAll(".table-filter").forEach((filter) => {
        const colIndex = filter.parentElement.getAttribute('col-index');
        filter.innerHTML = '<option value="all">All</option>'; // Add 'All' option first
        uniqueColValuesDict[colIndex].forEach((value) => {
            filter.innerHTML += `<option value="${value}">${value}</option>`;
        });
    });
}

// Create filterRows() function
function filterRows() {
    const filterValueDict = {};
    document.querySelectorAll(".table-filter").forEach((filter) => {
        const colIndex = filter.parentElement.getAttribute('col-index');
        const value = filter.value;
        if (value !== "all") {
            filterValueDict[colIndex] = value;
        }
    });

    const rows = document.querySelectorAll("#emp-table tbody tr");
    rows.forEach((row) => {
        let displayRow = true;

        for (const colIndex in filterValueDict) {
            const filterValue = filterValueDict[colIndex];
            const cellValue = row.querySelector(`td:nth-child(${colIndex})`).innerHTML.trim();

            if (cellValue.indexOf(filterValue) === -1) {
                displayRow = false;
                break;
            }
        }

        row.style.display = displayRow ? "table-row" : "none";
    });
}

// Event listeners to trigger the filtering
document.querySelectorAll(".table-filter").forEach((filter) => {
    filter.addEventListener("change", filterRows);
});