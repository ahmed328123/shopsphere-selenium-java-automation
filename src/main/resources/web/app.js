const zustand={produkte:[],warenkorb:JSON.parse(localStorage.getItem('shopsphere-warenkorb')||'[]')};

async function api(pfad,optionen={}){
 const antwort=await fetch(pfad,{headers:{'Content-Type':'application/json'},...optionen});
 const daten=await antwort.json().catch(()=>({}));
 if(!antwort.ok)throw new Error(daten.meldung||`HTTP ${antwort.status}`);
 return daten;
}
function anzeigen(id){document.querySelectorAll('.ansicht').forEach(e=>e.classList.add('versteckt'));document.getElementById(id).classList.remove('versteckt')}
function toast(text){const e=document.querySelector('[data-testid=toast]');e.textContent=text;e.classList.remove('versteckt');setTimeout(()=>e.classList.add('versteckt'),900)}
function warenkorbSpeichern(){localStorage.setItem('shopsphere-warenkorb',JSON.stringify(zustand.warenkorb));document.querySelector('[data-testid=warenkorb-anzahl]').textContent=zustand.warenkorb.reduce((s,p)=>s+p.menge,0)}

document.querySelector('[data-testid=anmelden]').onclick=async()=>{
 const email=document.querySelector('[data-testid=email]').value.trim();
 const passwort=document.querySelector('[data-testid=passwort]').value;
 const fehler=document.querySelector('[data-testid=anmelde-fehler]');fehler.classList.add('versteckt');
 try{await api('/api/anmeldung',{method:'POST',body:JSON.stringify({email,passwort})});document.getElementById('anmeldung').classList.add('versteckt');document.getElementById('anwendung').classList.remove('versteckt');document.getElementById('navigation').classList.remove('versteckt');await produkteLaden();warenkorbSpeichern()}
 catch(e){fehler.textContent=e.message;fehler.classList.remove('versteckt')}
};
document.querySelector('[data-testid=abmelden]').onclick=()=>{document.getElementById('anwendung').classList.add('versteckt');document.getElementById('navigation').classList.add('versteckt');document.getElementById('anmeldung').classList.remove('versteckt')};
document.querySelector('[data-testid=nav-produkte]').onclick=()=>anzeigen('produkte-ansicht');
document.querySelector('[data-testid=nav-warenkorb]').onclick=()=>{warenkorbRendern();anzeigen('warenkorb-ansicht')};
document.querySelector('[data-testid=nav-bestellungen]').onclick=async()=>{await bestellungenLaden();anzeigen('bestellungen-ansicht')};
document.querySelector('[data-testid=nav-profil]').onclick=()=>anzeigen('profil-ansicht');

async function produkteLaden(){zustand.produkte=await api('/api/produkte');produkteRendern()}
function produktSymbol(kategorie,index){const symbole={Elektronik:['🎧','⌨️','⌚'],Sport:['👟'],Haushalt:['☕'],Bücher:['📘']};const liste=symbole[kategorie]||['🛍️'];return liste[index%liste.length]}
function produkteRendern(){
 const suche=document.querySelector('[data-testid=suche]').value.toLowerCase();
 const kategorie=document.querySelector('[data-testid=kategorie]').value;
 const sortierung=document.querySelector('[data-testid=sortierung]').value;
 let liste=zustand.produkte.filter(p=>(p.name.toLowerCase().includes(suche)||p.kategorie.toLowerCase().includes(suche))&&(!kategorie||p.kategorie===kategorie));
 if(sortierung==='preis-auf')liste.sort((a,b)=>a.preis-b.preis);
 if(sortierung==='preis-ab')liste.sort((a,b)=>b.preis-a.preis);
 if(sortierung==='bewertung-ab')liste.sort((a,b)=>b.bewertung-a.bewertung);
 const wurzel=document.getElementById('produktliste');wurzel.innerHTML='';
 liste.forEach((p,index)=>{const karte=document.createElement('article');karte.className='produktkarte';karte.dataset.testid='produktkarte';karte.innerHTML=`<span class="bestand">${p.bestand} verfügbar</span><div class="produktbild farb-${index%3+1}">${produktSymbol(p.kategorie,index)}</div><span class="kategorie-chip">${p.kategorie.toUpperCase()}</span><h3>${p.name}</h3><div class="produkt-meta"><span>★ ${p.bewertung}</span><span>Artikel ${p.id}</span></div><div class="produkt-fuss"><span class="preis">€${p.preis.toFixed(2)}</span><button class="hinzufuegen" data-testid="hinzufuegen" data-id="${p.id}">Hinzufügen</button></div>`;wurzel.appendChild(karte)})
}
document.querySelector('[data-testid=suche]').addEventListener('input',produkteRendern);
// BEKANNTE FEHLER BUG-001 bis BUG-005: Nach Kategorie- oder Sortieränderung
// wird produkteRendern() in Version 1.0 absichtlich nicht aufgerufen.
document.querySelector('[data-testid=kategorie]').addEventListener('change',()=>{});
document.querySelector('[data-testid=sortierung]').addEventListener('change',()=>{});
document.getElementById('produktliste').onclick=e=>{if(e.target.dataset.testid!=='hinzufuegen')return;const id=Number(e.target.dataset.id),p=zustand.produkte.find(x=>x.id===id),vorhanden=zustand.warenkorb.find(x=>x.id===id);if(vorhanden)vorhanden.menge++;else zustand.warenkorb.push({...p,menge:1});warenkorbSpeichern();toast('Produkt wurde zum Warenkorb hinzugefügt')};

function warenkorbRendern(){const wurzel=document.getElementById('warenkorb-positionen');wurzel.innerHTML='';zustand.warenkorb.forEach(p=>{const zeile=document.createElement('div');zeile.className='warenkorb-zeile';zeile.dataset.testid='warenkorb-zeile';zeile.innerHTML=`<div><strong>${p.name}</strong><div>€${p.preis.toFixed(2)} pro Stück</div></div><div class="warenkorb-aktionen"><button data-testid="verringern" data-id="${p.id}">−</button><strong data-testid="menge">${p.menge}</strong><button data-testid="erhoehen" data-id="${p.id}">+</button><button class="entfernen" data-testid="entfernen" data-id="${p.id}">Entfernen</button></div>`;wurzel.appendChild(zeile)});document.querySelector('[data-testid=warenkorb-summe]').textContent=zustand.warenkorb.reduce((s,p)=>s+p.preis*p.menge,0).toFixed(2);const m=document.querySelector('[data-testid=warenkorb-meldung]');m.textContent=zustand.warenkorb.length?'':'Ihr Warenkorb ist leer';m.classList.toggle('versteckt',zustand.warenkorb.length!==0)}
document.getElementById('warenkorb-positionen').onclick=e=>{const id=Number(e.target.dataset.id),p=zustand.warenkorb.find(x=>x.id===id);if(!p)return;if(e.target.dataset.testid==='erhoehen')p.menge++;if(e.target.dataset.testid==='verringern')p.menge=Math.max(1,p.menge-1);if(e.target.dataset.testid==='entfernen')zustand.warenkorb=zustand.warenkorb.filter(x=>x.id!==id);warenkorbSpeichern();warenkorbRendern()};
document.querySelector('[data-testid=zur-kasse]').onclick=()=>{if(!zustand.warenkorb.length){const m=document.querySelector('[data-testid=warenkorb-meldung]');m.textContent='Ihr Warenkorb ist leer';m.classList.remove('versteckt');return}anzeigen('kasse-ansicht')};
document.querySelector('[data-testid=bestellung-aufgeben]').onclick=async()=>{const name=document.querySelector('[data-testid=kasse-name]').value.trim(),adresse=document.querySelector('[data-testid=kasse-adresse]').value.trim(),stadt=document.querySelector('[data-testid=kasse-stadt]').value.trim(),zahlungsart=document.querySelector('[data-testid=zahlungsart]').value,fehler=document.querySelector('[data-testid=kasse-fehler]');fehler.classList.add('versteckt');if(!name||!adresse||!stadt||!zahlungsart){fehler.textContent='Alle Checkout-Felder sind erforderlich';fehler.classList.remove('versteckt');return}const summe=zustand.warenkorb.reduce((s,p)=>s+p.preis*p.menge,0);await api('/api/bestellungen',{method:'POST',body:JSON.stringify({kunde:name,summe})});zustand.warenkorb=[];warenkorbSpeichern();await bestellungenLaden();anzeigen('bestellungen-ansicht');toast('Bestellung wurde bestätigt')};
async function bestellungenLaden(){const liste=await api('/api/bestellungen'),wurzel=document.getElementById('bestellungsliste');wurzel.innerHTML='';liste.forEach(b=>{const zeile=document.createElement('div');zeile.className='bestellung-zeile';zeile.dataset.testid='bestellung-zeile';zeile.innerHTML=`<div><strong>Bestellung #${b.id}</strong><div>${b.kunde}</div></div><div><strong>${b.status}</strong><div>€${b.summe.toFixed(2)}</div></div>`;wurzel.appendChild(zeile)})}
document.querySelector('[data-testid=profil-speichern]').onclick=()=>{const m=document.querySelector('[data-testid=profil-meldung]');m.textContent='Profil wurde gespeichert';m.classList.remove('versteckt')};
