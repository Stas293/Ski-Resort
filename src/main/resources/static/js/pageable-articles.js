let urlPath;

const Paths = {
    EUROPE: '/api/v1/resorts',
    NORTH_AMERICA: '/api/v1/resorts',
    SOUTH_AMERICA: '/api/v1/resorts',
    OTHER_WORLD: '/api/v1/resorts',
    NEWS: '/api/v1/news',
    EVENTS: '/api/v1/events'
}

const Continents = {
    EUROPE: 'Europe',
    NORTH_AMERICA: 'North America',
    SOUTH_AMERICA: 'South America',
    OTHER_WORLD: 'Other World'
}

const Types = {
    EUROPE: 'resorts',
    NORTH_AMERICA: 'resorts',
    SOUTH_AMERICA: 'resorts',
    OTHER_WORLD: 'resorts',
    NEWS: 'news',
    EVENTS: 'events'
}

window.onload = function () {
    let bodyClass = $('body')[0].getAttribute('id').toString();
    console.log(bodyClass);
    let continent = Continents[bodyClass.toUpperCase()];
    console.log(continent);
    urlPath = Paths[bodyClass.toUpperCase()];
    console.log(urlPath);
    continent === undefined ? wizard(urlPath) : wizard(`${urlPath}?continent=${continent}`);
};

class Card {
    constructor(id, imgSrc, imgAlt, title, text, date) {
        this.id = id;
        this.imgSrc = imgSrc;
        this.imgAlt = imgAlt;
        this.title = title;
        this.text = text;
    }

    static from = function (rowData) {
        return new Card(
        rowData.id,
        rowData.image,
        rowData.imageAlt,
        rowData.title,
        rowData.description);
    }
}

const makeRow = function (rowData) {
    let card = Card.from(rowData);
    console.log(card);
    let article = document.createElement('article');
    article.setAttribute('class', 'card');

    let bodyClass = document.getElementsByTagName('body')[0].getAttribute('id').toString();
    let articleType = Types[bodyClass.toUpperCase()];

    let anchor = document.createElement('a');
    anchor.setAttribute('href', `/${articleType}/${card.id}`);

    let img = document.createElement('img')
    img.setAttribute('src', `${urlPath}/${card.id}/image`);
    img.setAttribute('alt', card.imgAlt);
    img.setAttribute('class', 'default-image');

    let cardBody = document.createElement('div');
    cardBody.setAttribute('class', 'card-body');

    let title = document.createElement('h5');
    title.setAttribute('class', 'card-title');
    title.appendChild(document.createTextNode(card.title));

    let text = document.createElement('p');
    text.setAttribute('class', 'card-text line-clamp');
    text.appendChild(document.createTextNode(card.text));

    cardBody.appendChild(title);
    cardBody.appendChild(text);
    anchor.appendChild(img);
    anchor.appendChild(cardBody);
    article.appendChild(anchor);
    return article;
};
