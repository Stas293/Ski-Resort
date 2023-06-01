const MAX_WIDTH = 500;

function setWidthHeight(image, MAX_SIZE) {
    let width = image.width;
    let height = image.height;
    if (width > height) {
        if (width > MAX_SIZE) {
            height *= MAX_SIZE / width;
            width = MAX_SIZE;
        }
    } else {
        if (height > MAX_SIZE) {
            width *= MAX_SIZE / height;
            height = MAX_SIZE;
        }
    }
    return {width, height};
}

function getOnload(image, preview) {
    return () => {
        const canvas = document.createElement('canvas');
        const ctx = canvas.getContext('2d');
        let MAX_SIZE = window.innerWidth > MAX_WIDTH ? MAX_WIDTH : window.innerWidth - 20;
        let {width, height} = setWidthHeight(image, MAX_SIZE);
        canvas.width = width;
        canvas.height = height;
        ctx.drawImage(image, 0, 0, width, height);
        const squareCanvas = document.createElement('canvas');
        const squareCtx = squareCanvas.getContext('2d');
        const squareSize = Math.min(width, height);
        squareCanvas.width = squareSize;
        squareCanvas.height = squareSize;
        const x = (width - squareSize) / 2;
        const y = (height - squareSize) / 2;
        squareCtx.drawImage(canvas, x, y, squareSize, squareSize, 0, 0, squareSize, squareSize);
        preview.src = squareCanvas.toDataURL('image/jpeg');
        preview.style.display = 'block';
    };
}

function previewImage() {
    const reader = new FileReader();
    reader.onload = function () {
        const preview = document.getElementById('preview');
        const image = new Image();
        image.src = reader.result;
        image.onload = getOnload(image, preview)
        window.onresize = previewImage;
    }
    reader.readAsDataURL(document.getElementById('image').files[0]);
}

function removePicture() {
    const preview = document.getElementById('preview');
    preview.src = '';
    preview.style.display = 'none';
    document.getElementById('image').value = '';
}

