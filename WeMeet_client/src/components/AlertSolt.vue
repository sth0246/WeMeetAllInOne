<template>
    <div class="search">
        <div class="form">
            <slot name="input"></slot>
            <slot name="btn"></slot>
        </div>
    </div>
</template>
<script setup>

</script>

<style scoped>
.search {

    position: absolute;
    background-image: url('https://wallpaperm.cmcm.com/9be82be4220ae47a5009a055961c16d7.jpg');
    background-position: center;
    background-size: contain;
    top: 30%;
    left: 35%;
    height: 146px;
    width: 253px;
    background-color: rgb(255, 248, 225);
    border-radius: 8px;
    overflow: hidden;
    z-index: 9;
}

.form {
    position: absolute;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    z-index: 0;
    width: 263px;
    height: 156px;
    border-radius: 8px;
    backdrop-filter: blur(5px);
    box-shadow: 0 0 30px 10px rgba(0, 0, 0, .3);
}
</style>