document.addEventListener('DOMContentLoaded', () => {
    // Paleta MetaMapa
    const C = {
        brand800: '#3B4F61',
        brand500: '#86A8C2',
        accent: '#AFCBE3',
        green: '#6FCF97',
        red: '#EB5757',
        softBlue: '#EAF3FA',
        softGreen: '#E1F2EA'
    };

    // Tipografía global coherente con el sitio
    Chart.defaults.font.family = getComputedStyle(document.body).fontFamily;
    Chart.defaults.color = C.brand800;

    // Opciones base para barras/líneas
    const baseXY = {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {legend: {display: false}},
        scales: {
            x: {grid: {display: false}},
            y: {
                beginAtZero: true,
                grid: {color: 'rgba(59,79,97,.08)'},
                ticks: {precision: 0}
            }
        }
    };

    // === 1) Barras: Hechos por provincia (colección) ===
    /*new Chart(document.getElementById('chartProvColeccion'), {
        type: 'bar',
        data: {
            labels: ['Buenos Aires', 'Santa Fe', 'Córdoba', 'Mendoza', 'Neuquén'],
            datasets: [{
                label: 'Hechos',
                data: [120, 95, 80, 55, 40],
                backgroundColor: C.brand500,
                borderRadius: 8
            }]
        },
        options: baseXY
    });

    // === 2) Barras horizontales: Hechos por categoría ===
    new Chart(document.getElementById('chartCategorias'), {
        type: 'bar',
        data: {
            labels: ['Defensa civil', 'Contaminación', 'Derrame', 'Incendio', 'Otros'],
            datasets: [{
                data: [250, 180, 140, 90, 60],
                backgroundColor: [C.brand500, C.accent, '#C9DEEC', '#B7CFE2', '#9FBBD2'],
                borderRadius: 8
            }]
        },
        options: {
            ...baseXY,
            indexAxis: 'y'
        }
    });

    // === 3) Doughnut: Porcentaje de spam ===
    new Chart(document.getElementById('chartSpam'), {
        type: 'doughnut',
        data: {
            labels: ['Spam', 'Válidas'],
            datasets: [{
                data: [90, 10],
                backgroundColor: [C.red, C.softGreen],
                borderWidth: 0
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            cutout: '62%',
            plugins: {
                legend: {position: 'bottom'},
                tooltip: {
                    callbacks: {
                        label: ctx => `${ctx.label}: ${ctx.raw}%`
                    }
                }
            }
        }
    });
    */
    const findStat = (data,nombre) =>
        (data || []).find(e => e.nombre === nombre) || {datos: []};
    const data = agregarDatos()
    function agregarDatos() {
        return ['COLECCIONPROVINCIAMAYORHECHOS', 'CATEGORIATOP', 'SOLICITUDESSPAM', 'CATEGORIAPROVINCIAMAYORHECHOS',
        'CATEGORIAHORAMAYORHECHOS'].map(tipoEstadistica => {
            const estadistica = document.getElementById(tipoEstadistica)
            const totalDatos = Number(estadistica.querySelector('.totalEstadistica').textContent)
            const resultado = {nombre: tipoEstadistica, datos: []}
            if (totalDatos === 0)
                return resultado

            for (let i = 0; i< totalDatos; i++){
                const total = Number(estadistica.querySelector('.datoEstadistico_' + i +'_total').textContent)
                const cantidad = Number(estadistica.querySelector('.datoEstadistico_' + i +'_cantidad').textContent)
                const hora = estadistica.querySelector('.datoEstadistico_' + i +'_hora').textContent
                const porcentaje = Number(estadistica.querySelector('.datoEstadistico_' + i +'_porcentaje').textContent)
                const primerCriterio = estadistica.querySelector('.datoEstadistico_' + i +'_primerCriterio').textContent
                const segundoCriterio = estadistica.querySelector('.datoEstadistico_' + i +'_segundoCriterio').textContent
                resultado.datos.push({total, cantidad, hora, porcentaje, primerCriterio, segundoCriterio})
            }
            return resultado
        })

    }
            // === Barras: Hechos por provincia (colección) ===
            const estProvCol = findStat(data, 'COLECCIONPROVINCIAMAYORHECHOS');
            const provLabels = estProvCol.datos.map(d => d.primerCriterio); // provincia
            const provValues = estProvCol.datos.map(d => d.cantidad ?? 0);

            new Chart(document.getElementById('chartProvColeccion'), {
                type: 'bar',
                data: {
                    labels: provLabels,
                    datasets: [{
                        label: 'Hechos',
                        data: provValues,
                        backgroundColor: C.brand500,
                        borderRadius: 8
                    }]
                },
                options: baseXY
            });
            const top = estProvCol.datos[0];
            if (top) {
                const chipColeccion = document.getElementById('chip-colect-province');
                const chipProvincia = document.getElementById('chip-provincia');
                const chipCant = document.getElementById('chip-prov-cantidad');

                chipColeccion && (chipColeccion.textContent = top.segundoCriterio || '-');
                chipProvincia && (chipProvincia.textContent = top.primerCriterio || '-');
                chipCant && (chipCant.textContent = top.cantidad ?? '-');
            }

            // === Barras horizontales: Hechos por categoría ===
            const estCategorias = findStat(data, 'CATEGORIATOP');
            const catLabels = estCategorias.datos.map(d => d.primerCriterio);   // nombre categoría
            const catValues = estCategorias.datos.map(d => d.cantidad ?? 0);

            new Chart(document.getElementById('chartCategorias'), {
                type: 'bar',
                data: {
                    labels: catLabels,
                    datasets: [{
                        data: catValues,
                        backgroundColor: [C.brand500, C.accent, '#C9DEEC', '#B7CFE2', '#9FBBD2'],
                        borderRadius: 8
                    }]
                },
                options: {
                    ...baseXY,
                    indexAxis: 'y'
                }
            });

            // === Doughnut: Porcentaje de spam ===
            const estSpam = findStat(data, 'SOLICITUDESSPAM');
            const spamData = estSpam.datos[0] || {};
            const spamCount = spamData.cantidad ?? 0;
            const totalCount = spamData.total ?? 0;
            const spamPct = totalCount > 0 ? Math.round((spamCount * 100) / totalCount) : 0;
            const validPct = 100 - spamPct;

            new Chart(document.getElementById('chartSpam'), {
                type: 'doughnut',
                data: {
                    labels: ['Spam', 'Válidas'],
                    datasets: [{
                        data: [spamPct, validPct],
                        backgroundColor: [C.red, C.softGreen],
                        borderWidth: 0
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    cutout: '62%',
                    plugins: {
                        legend: {position: 'bottom'},
                        tooltip: {
                            callbacks: {
                                label: ctx => `${ctx.label}: ${ctx.raw}%`
                            }
                        }
                    }
                }
            });

            // Actualizar textos de resumen
            const chipSpamPorcentaje = document.getElementById('chip-spam-porcentaje');
            const chipSpamTotal = document.getElementById('chip-spam-total');
            chipSpamPorcentaje && (chipSpamPorcentaje.textContent = `${spamPct}%`);
            chipSpamTotal && (chipSpamTotal.textContent = totalCount);


});


