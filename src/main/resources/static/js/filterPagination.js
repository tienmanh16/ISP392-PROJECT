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

    const isNumeric = (str) => {
        if (typeof str != "string") return false; // we only process strings!
        return !isNaN(str) && // use type coercion to parse the entirety of the string (`parseFloat` alone does not do this)
            !isNaN(parseFloat(str)); // ensure strings of whitespace fail
    };

    filteredRows = Array.from(rows).filter((row) => {
        for (const colIndex in filterValueDict) {
            const filterValue = filterValueDict[colIndex];
            const cellValue = row.querySelector(`td:nth-child(${colIndex})`).innerHTML.trim();

            // If the column is numeric, perform an exact match
            if (isNumeric(filterValue) && isNumeric(cellValue)) {
                if (parseFloat(cellValue) !== parseFloat(filterValue)) {
                    return false;
                }
            } else if (cellValue.indexOf(filterValue) === -1) {
                return false;
            }
        }
        return true;
    });

    currentPage = 1; // Reset to the first page after filtering
    displayRows();
}

// Event listeners to trigger the filtering
document.querySelectorAll(".table-filter").forEach((filter) => {
    filter.addEventListener("change", filterRows);
});

// Sorting function
function sortTableByColumn(columnIndex, ascending) {
    const dirModifier = ascending ? 1 : -1;
    filteredRows.sort((a, b) => {
        const aText = a.querySelector(`td:nth-child(${columnIndex})`).textContent.trim();
        const bText = b.querySelector(`td:nth-child(${columnIndex})`).textContent.trim();
        const aValue = parseFloat(aText) || 0;
        const bValue = parseFloat(bText) || 0;

        return (aValue - bValue) * dirModifier;
    });

    currentPage = 1; // Reset to the first page after sorting
    displayRows();
}

// Add event listeners for sorting buttons
document.getElementById('sort-asc-price-hour').addEventListener('click', () => {
    sortTableByColumn(4, true); // Assuming priceHour is the 4th column (index 4)
});

document.getElementById('sort-desc-price-hour').addEventListener('click', () => {
    sortTableByColumn(4, false); // Assuming priceHour is the 4th column (index 4)
});

// Add event listeners for sorting buttons
document.getElementById('sort-asc-price-day').addEventListener('click', () => {
    sortTableByColumn(5, true); // Assuming priceDay is the 5th column (index 5)
});

document.getElementById('sort-desc-price-day').addEventListener('click', () => {
    sortTableByColumn(5, false); // Assuming priceDay is the 5th column (index 5)
});

// Add event listeners for sorting buttons
document.getElementById('sort-asc-price-hour').addEventListener('click', () => {
    sortTableByColumn(5, true); // Assuming priceHour is the 4th column (index 4)
});

document.getElementById('sort-desc-price-hour').addEventListener('click', () => {
    sortTableByColumn(5, false); // Assuming priceHour is the 4th column (index 4)
});

// Add event listeners for sorting buttons
document.getElementById('sort-asc-price-day').addEventListener('click', () => {
    sortTableByColumn(6, true); // Assuming priceDay is the 5th column (index 5)
});

document.getElementById('sort-desc-price-day').addEventListener('click', () => {
    sortTableByColumn(6, false); // Assuming priceDay is the 5th column (index 5)
});

// Add event listeners for sorting buttons
document.getElementById('sort-salary-asc').addEventListener('click', () => {
    sortTableByColumn(10, true); // Assuming priceDay is the 5th column (index 5)
});

document.getElementById('sort-salary-desc').addEventListener('click', () => {
    sortTableByColumn(10, false); // Assuming priceDay is the 5th column (index 5)
});
// Initial display
getUniqueValuesFromColumn();
displayRows();

