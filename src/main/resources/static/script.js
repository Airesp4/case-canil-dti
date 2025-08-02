const petShops = [
    {
        name: "Meu Canino Feliz",
        distance: 2.0,
        weekend: {
            small: 24.0,
            large: 48.0
        },
        weekday: {
            small: 20.0,
            large: 40.0
        }
    },
    {
        name: "Vai Rex",
        distance: 1.7,
        weekend: {
            small: 20.0,
            large: 55.0
        },
        weekday: {
            small: 15.0,
            large: 50.0
        }
    },
    {
        name: "ChowChawgas",
        distance: 0.8,
        weekend: {
            small: 30.0,
            large: 45.0
        },
        weekday: {
            small: 30.0,
            large: 45.0
        }
    }
];

function findPetShopByName(name) {
    return petShops.find(shop => shop.name === name);
}

async function calculateBestShop() {
    clearError();

    const date = document.getElementById('date').value;
    const smallDogsStr = document.getElementById('small-dogs').value;
    const bigDogsStr = document.getElementById('big-dogs').value;

    if (!date) {
        showError('Por favor, preencha a data!');
        return;
    }

    if (!validateDogCounts(smallDogsStr, bigDogsStr)) {
        return;
    }

    const smallDogs = Number(smallDogsStr);
    const bigDogs = Number(bigDogsStr);

    try {
        const response = await fetch('/best', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                date: date,
                smallDogs: smallDogs,
                bigDogs: bigDogs
            })
        });

        if (!response.ok) {
            throw new Error('Erro ao calcular melhor opção');
        }

        const result = await response.json();
        const shop = findPetShopByName(result.name);

        if (!shop) {
            throw new Error('Petshop não encontrado na lista local.');
        }

        document.getElementById('best-shop-name').textContent = result.name;
        document.getElementById('distance').textContent = `📍 ${shop.distance.toFixed(1)} km`;
        document.getElementById('total-price').textContent = `Preço Total: R$ ${result.totalPrice.toFixed(2)}`;
        document.getElementById('breakdown').textContent = '';

        document.getElementById('result').style.display = 'block';
    } catch (error) {
        showError('Erro ao calcular: ' + error.message);
    }
}

function reset() {
    clearError();
    document.getElementById('date').value = '';
    document.getElementById('small-dogs').value = '';
    document.getElementById('big-dogs').value = '';
    document.getElementById('result').style.display = 'none';
}

const errorElement = document.getElementById('form-error');

function showError(message) {
    errorElement.textContent = message;
    errorElement.style.display = 'block';
}

function clearError() {
    errorElement.textContent = '';
    errorElement.style.display = 'none';
}

function validateDogCounts(smallDogsStr, bigDogsStr) {
    if (smallDogsStr === '' || bigDogsStr === '') {
        showError('Por favor, preencha todos os campos!');
        return false;
    }

    const smallDogs = Number(smallDogsStr);
    const bigDogs = Number(bigDogsStr);

    if (
        !Number.isInteger(smallDogs) || smallDogs < 0 ||
        !Number.isInteger(bigDogs) || bigDogs < 0
    ) {
        showError('A quantidade de cães deve ser um número inteiro não negativo.');
        return false;
    }

    return true;
}