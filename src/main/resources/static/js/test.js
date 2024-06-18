
    const checkboxes1 = document.querySelectorAll('input[name="statusFilter"]');
    checkboxes1.forEach((checkbox) => {
        checkbox.addEventListener('change', function() {

            if (checkbox.checked) {
                const statusFilter = (checkbox.value);
                const url = `filterRoom?statusFilter=${statusFilter}`;
                fetch(url).then((response) => response.text());
                console.log(url);
            }

        });
    });

   

   