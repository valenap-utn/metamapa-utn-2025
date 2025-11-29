//JS para FUENTES y URLs
(function () {
    const tipoEl = document.getElementById('tipoFuente');
    const urlEl = document.getElementById('url');
    const addBtn = document.getElementById('addFuente');
    const cont = document.getElementById('fuentesContainer');

    if (!tipoEl || !urlEl || !addBtn || !cont) return;

    const fuentes = []; // {tipo, url}
    agregarFuentesYaHechas(fuentes)
    // Normaliza: agrega https:// si falta y recorta espacios
    function normalizeUrl(v) {
        let s = (v || '').trim();
        if (!s) return '';
        if (!/^https?:\/\//i.test(s)) s = 'https://' + s;
        try {
            s = new URL(s).href;
        } catch (e) { /* deja tal cual si no parsea */
        }
        return s;
    }

    const urlOk = v => /^https?:\/\/\S+\.?\S*/.test(v); // simple pero útil

    function render() {
        cont.innerHTML = '';
        fuentes.forEach((f, i) => {
            const chip = document.createElement('span');
            chip.className = 'chip';
            chip.innerHTML = `
                        <input type="text" style="display: none;" name="fuentes[${i}].tipoOrigen" value="${f.tipo}" />
                        <input type="text" style="display: none;" name="fuentes[${i}].url" value="${f.url}"/>
                        <strong>${f.tipo}</strong> · ${f.url}
                        <button type="button" class="chip__remove" aria-label="Quitar fuente"><span aria-hidden="true">×</span></button>`;
            chip.querySelector('.chip__remove').addEventListener('click', () => {
                fuentes.splice(i, 1);
                render();
            });
            cont.appendChild(chip);
        });
        //hidden.value = JSON.stringify(fuentes);
    }

    function validate() {
        const candidate = normalizeUrl(urlEl.value);
        const ok = urlOk(candidate);
        addBtn.disabled = !ok;
        urlEl.classList.toggle('is-invalid', urlEl.value.trim() !== '' && !ok);
        return ok;
    }

    // Autocorrección al salir del campo / pegar
    function autocorrect() {
        const normalized = normalizeUrl(urlEl.value);
        if (urlEl.value.trim() && urlEl.value !== normalized) {
            urlEl.value = normalized;
        }
        validate();
    }

    addBtn.addEventListener('click', () => {
        const tipo = (tipoEl.value || '').trim() || '—';
        const url = normalizeUrl(urlEl.value);
        if (!urlOk(url)) {
            urlEl.classList.add('is-invalid');
            urlEl.focus();
            return;
        }
        // evita duplicados por URL
        if (fuentes.some(f => f.url.toLowerCase() === url.toLowerCase())) {
            urlEl.value = '';
            validate();
            return;
        }
        fuentes.push({tipo, url});
        tipoEl.value = '';
        urlEl.value = '';
        validate();
        render();
    });

    urlEl.addEventListener('blur', autocorrect);
    urlEl.addEventListener('paste', setTimeout.bind(null, autocorrect, 0));
    urlEl.addEventListener('input', validate);
    urlEl.addEventListener('change', validate);
    urlEl.addEventListener('keydown', e => {
        if (e.key === 'Enter') {
            e.preventDefault();
            addBtn.click();
        }
    });
    function agregarFuentesYaHechas(fuentes) {
        const container = document.getElementById("fuentesContainer")
        const fuentesExistentes = container.querySelectorAll("span.chip")
        if(fuentesExistentes.length > 0) {
            fuentesExistentes.forEach((fuente, indice) => {
                const tipo = document.getElementById(`fuentes[${indice}].tipoOrigen`).value
                const url = document.getElementById(`fuentes[${indice}].url`).value
                fuentes.push({tipo, url});
                fuente.querySelector('.chip__remove').addEventListener('click', () => {
                    fuentes.splice(indice, 1);
                    render();
                });
            })

        }
    }
    validate();
    render();
})();



//JS para CRITERIOS de PERTENENCIA
(function () {
    const tipoSel = document.getElementById('critTipo');
    const wrap = document.getElementById('critValorWrap');
    const addBtn = document.getElementById('addCriterio');
    const chips = document.getElementById('criteriosChips');
    const categorias = []
    if (!tipoSel || !wrap || !addBtn || !chips) return;

    const criterios = []; // { tipo, label, ...valores }

    const todayISO = () => new Date().toISOString().slice(0, 10);
    cargarCategorias(categorias)
    // --- configuraciones por tipo ---
    const CFG = {
        CATEGORIA: {
            label: 'Categoría',
            render() {
                wrap.innerHTML = `
          <label class="mm-label" for="critValorCategoria">Valor</label>
          <select id="critValorCategoria" class="mm-input" onchange="modificarCategoria(this)" >
            <option value="" disabled selected>Seleccioná una categoría…</option>
            <option value="EVENTO_SANITARIO">Evento sanitario</option>
            <option value="">Crear una categoria…</option>
            <option value="CONTAMINACION">Contaminación</option>
            <option value="DERRAME">Derrame</option>
            ${mapearCategorias()}
          </select>
          <label class="mm-label" style="display: none;" id="critValorCategoriaInputLabel" for="critValorCategoria">Categoria</label>
          <input id="critValorCategoriaInput" style="display: none;"  class="mm-input" type="text">
        `;
            },
            read() {
                const el = document.getElementById('critValorCategoria');
                const valorEl = el && (el.value !== "") ? {value: el.value, text: el.options[el.selectedIndex].text} : null;
                if (valorEl != null)
                    return valorEl;
                const el2 = document.getElementById('critValorCategoriaInput');
                const v = (el2?.value || '').trim();
                if (v.length < 3) {
                    el2?.classList.add('is-invalid');
                    return null;
                }
                el2.classList.remove('is-invalid');
                return {value: el2.value, text: el2.value};
            }
        },

        TITULO: {
            label: 'Título contiene',
            render() {
                wrap.innerHTML = `
          <label class="mm-label" for="critValorText">Valor</label>
          <input id="critValorText" class="mm-input" placeholder="Ej: 'río', 'incendio'…" />
          <small class="mm-help">Mínimo 3 caracteres.</small>
        `;
            },
            read() {
                const el = document.getElementById('critValorText');
                const v = (el?.value || '').trim();
                if (v.length < 3) {
                    el?.classList.add('is-invalid');
                    return null;
                }
                el.classList.remove('is-invalid');
                return {value: v};
            }
        },

        UBICACIONPROVINCIA: {
            label: 'Ubicación Provincia',
            render() {
                wrap.innerHTML = `
          <label class="mm-label" for="critValorUbic">Valor</label>
          <input id="critValorUbic" class="mm-input" placeholder="Provincia"/>
        `;
            },
            read() {
                const el = document.getElementById('critValorUbic');
                const v = (el?.value || '').trim();
                if (!v) {
                    el?.classList.add('is-invalid');
                    return null;
                }
                el.classList.remove('is-invalid');
                return {value: v};
            }
        },
        UBICACIONMUNICIPIO: {
            label: 'Ubicación Municipio',
            render() {
                wrap.innerHTML = `
          <label class="mm-label" for="critValorUbic">Valor</label>
          <input id="critValorUbic" class="mm-input" placeholder="Provincia"/>
        `;
            },
            read() {
                const el = document.getElementById('critValorUbic');
                const v = (el?.value || '').trim();
                if (!v) {
                    el?.classList.add('is-invalid');
                    return null;
                }
                el.classList.remove('is-invalid');
                return {value: v};
            }
        },
        UBICACIONDEPARTAMENTO: {
            label: 'Ubicación Departamento',
            render() {
                wrap.innerHTML = `
          <label class="mm-label" for="critValorUbic">Valor</label>
          <input id="critValorUbic" class="mm-input" placeholder="Provincia"/>
        `;
            },
            read() {
                const el = document.getElementById('critValorUbic');
                const v = (el?.value || '').trim();
                if (!v) {
                    el?.classList.add('is-invalid');
                    return null;
                }
                el.classList.remove('is-invalid');
                return {value: v};
            }
        },

        // === FECHAS segun lo que hablamos ===
        FECHAACONTECIMIENTO: multiDateCfg('Fecha de acontecimiento', false),
        FECHACARGA: multiDateCfg('Fecha de carga', true)
    };
    agregarCriteriosYaHechos(criterios)

    function cargarCategorias(categorias) {
        const categoriasContainer = document.getElementById('containerCategorias')
        const categoriasElement = categoriasContainer.querySelectorAll('option.categoria-agregador')
        if (categoriasElement.length > 0){
            categoriasElement.forEach((categoria, indice) => categorias.push({valueCategoria: categoria.text}))
        }
    }

    function mapearCategorias() {
        const valor = categorias.map(categoria => `<option value="${categoria.valueCategoria}">${categoria.valueCategoria}</option>`).reduce((categoriaPrevia, categoriaActual) => categoriaPrevia + '\n'+ categoriaActual, "")
        console.log(valor)
        return valor
    }


    // fecha exacta (un solo input)
    function multiDateCfg(label, esConCargaActual) {
        return {
            label,
            render() {
                wrap.innerHTML = `
          <label class="mm-label" for="critFechaInicial">${label} inicial</label>
          <input id="critFechaInicial" type="datetime-local" class="mm-input" >
          <label class="mm-label" for="critFechaFinal">${label} final</label>
          <input id="critFechaFinal" type="datetime-local" class="mm-input" value="${esConCargaActual ? todayISO(): ''}" >
        `;
            },
            read() {
                const el = document.getElementById('critFechaInicial');
                const v = el?.value || '';
                if (!v) {
                    el?.classList.add('is-invalid');
                } else {
                    el.classList.remove('is-invalid');
                }
                const el2 = document.getElementById('critFechaFinal');
                const v2 = el2?.value || '';
                if (!v2) {
                    el?.classList.add('is-invalid');
                } else {
                    el2.classList.remove('is-invalid');
                }
                return {desde: v, hasta: v2};
            }
        };
    }

    // fecha de carga = hoy (solo informativo en UI)
    function cargaTodayCfg(label) {
        return {
            label,
            render() {
                const hoy = todayISO();
                wrap.innerHTML = `
          <label class="mm-label">${label}</label>
          <input type="date" class="mm-input" value="${hoy}" disabled>
          <small class="mm-help">Se toma automáticamente la fecha actual (el backend debería confirmarla).</small>
        `;
            },
            read() {
                return {fecha: todayISO()};
            }
        };
    }

    // --- hooks UI ---
    tipoSel.addEventListener('change', () => {
        const cfg = CFG[tipoSel.value];
        if (cfg) cfg.render();
    });

    addBtn.addEventListener('click', () => {
        const tipo = tipoSel.value;
        const cfg = CFG[tipo];
        if (!cfg) return;

        const val = cfg.read();
        if (!val) return;

        const label = buildLabel(cfg.label, val);
        criterios.push({tipo, label, ...val});
        renderChips();
        persist();
    });

    function buildLabel(base, val) {
        if ('text' in val) return `${base}: ${val.text}`;
        if ('value' in val) return `${base}: ${val.value}`;
        if ('fecha' in val) return `${base}: ${val.fecha}`;
        // fallback por si en el futuro hay rango
        if ('desde' in val || 'hasta' in val) {
            const a = val.desde || '—';
            const b = val.hasta || '—';
            return `${base}: ${a} → ${b}`;
        }
        return base;
    }


    function renderChips() {
        const cont = document.getElementById('criteriosChips');
        cont.innerHTML = '';
        criterios.forEach((c, i) => {
            const chip = document.createElement('span');
            chip.className = 'mm-chip';
            chip.innerHTML = `
            <input type="text" style="display: none;" name="criterios[${i}].tipo" value="${c.tipo}"/>
            ${getInputSegunTipo(c, i)}       
            <b>${c.label}</b>
        <button type="button" class="chip__remove" aria-label="Quitar criterio">
          <span aria-hidden="true">×</span>
        </button>`;
            chip.querySelector('.chip__remove').addEventListener('click', () => {
                criterios.splice(i, 1);
                renderChips();
                persist();
            });
            cont.appendChild(chip);
        });
    }

    function getInputSegunTipo(c, i) {
        if(c.tipo === "CATEGORIA" ) {
            return `<input type="text" style="display: none;" name="criterios[${i}].categoria" value="${c.text}"/>`
        } else if( c.tipo === "TITULO") {
            return `<input type="text" style="display: none;" name="criterios[${i}].titulo" value="${c.value}"/>`
        } else if( c.tipo === "FECHAACONTECIMIENTO") {
            return`<input type="datetime-local" style="display: none;" name="criterios[${i}].fechaAcontecimientoInicial" value="${c.desde}"/>` + `<input type="datetime-local" style="display: none;" name="criterios[${i}].fechaAcontecimientoFinal" value="${c.hasta}"/>`
        } else if(c.tipo === "FECHACARGA") {
            return `<input type="datetime-local" style="display: none;" name="criterios[${i}].fechaCargaInicial" value="${c.desde}"/>` +
                `<input type="datetime-local" style="display: none;" name="criterios[${i}].fechaCargaFinal" value="${c.hasta}"/>`
        } else if(c.tipo === "UBICACIONPROVINCIA") {
            return `<input type="text" style="display: none;" name="criterios[${i}].provincia" value="${c.value}"/>`
        } else if (c.tipo === "UBICACIONMUNICIPIO") {
            return `<input type="text" style="display: none;" name="criterios[${i}].municipio" value="${c.value}"/>`
        } else if (c.tipo === "UBICACIONDEPARTAMENTO") {
            return `<input type="text" style="display: none;" name="criterios[${i}].departamento" value="${c.value}"/>`
        }
        return ""
    }

    function persist() {
        //hidden.value = JSON.stringify(criterios);
    }

    // estado inicial sugerido
    CFG.TITULO.render();
    function agregarCriteriosYaHechos(criterios) {
        const container = document.getElementById("criteriosChips")
        const criteriosExistentes = container.querySelectorAll("span.mm-chip")
        if(criteriosExistentes.length > 0) {
            criteriosExistentes.forEach((criterio, indice) => {
                const inputTipo = document.getElementById("Criterio."+ indice).value
                const cfg = CFG[inputTipo];
                if (!cfg) return;

                let val = undefined

                if (inputTipo === "TITULO") {
                    const titulo = document.getElementById(`criterios[${indice}].titulo`).value
                    val = {value: titulo }
                } else if (inputTipo === "CATEGORIA") {
                    const categoria = document.getElementById(`criterios[${indice}].categoria`).value
                    val = {text: categoria,  value: categoria}
                } else if (inputTipo === "FECHAACONTECIMIENTO") {
                    const fechaInicial = document.getElementById(`criterios[${indice}].fechaAcontecimientoInicial`).value
                    const fechaFinal = document.getElementById(`criterios[${indice}].fechaAcontecimientoFinal`).value
                    val = {desde: fechaInicial, hasta: fechaFinal }
                } else if (inputTipo === "FECHACARGA") {
                    const fechaInicial = document.getElementById(`criterios[${indice}].fechaCargaInicial`).value
                    const fechaFinal = document.getElementById(`criterios[${indice}].fechaCargaFinal`).value
                    val = {desde: fechaInicial, hasta: fechaFinal}
                } else if(inputTipo === "UBICACIONPROVINCIA") {
                    const provincia = document.getElementById(`criterios[${indice}].provincia`).value
                    val = {value: provincia}
                } else if (inputTipo === "UBICACIONMUNICIPIO") {
                    const municipio = document.getElementById(`criterios[${indice}].municipio`).value
                    val = {value: municipio}
                } else if (inputTipo === "UBICACIONDEPARTAMENTO") {
                    const departamento = document.getElementById(`criterios[${indice}].departamento`).value
                    val = {value: departamento}
                }
                const label = buildLabel(cfg.label, val);
                criterios.push({tipo:  inputTipo, label, ...val});
                criterio.querySelector('.chip__remove').addEventListener('click', () => {
                    criterios.splice(indice, 1);
                    renderChips();
                    persist();
                });
            })

        }
    }
})();

