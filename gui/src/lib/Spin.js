const spin = {
    onStart: null,
    onStop: null,

    start: () => spin.onStart && spin.onStart(),
    stop: () => spin.onStop && spin.onStop()
};

export default spin;
