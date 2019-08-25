### Aus c't wissen "DOCKER"
# Grundbegriff
# Ein Container enthaelt eine Software mit all ihren Abhaengigkeiten. Gestartet wird ein solcher Container aus einem Abbild, dem Container-Image.
# Basis eines jeden Docker-Containers ist ein Docker-Image. Ein solches Image beinhaltet nur die grundlegenden Programme, Bibliotheken und Daten.
# Aus einem Image lassen sich beliebig viele Container erzeugen.
# Images entstehen aus einem Bauplan, dem Dockerfile. Es enthaelt Anweisungen, auf welchem anderen Image ein neues Image ausbauen soll,
# kopiert die Software und Abhaengigheiten und ergaenzt Befehle, zum Beispiel Installationsskripte.
#
# Daten, die zum Beispiel in eine Datenbank geschrieben werden, sollen ein Neuerstellen des Containers ueberleben. Docker nutzt dafuer Volumes,
# also Ordner im lokalen Dateisystem, die in den Container uebergeben werden.

# Sie sollten den aktuellen Benutzer zur Gruppe Docker hinzufuegen, damit Sie auch als normaler Benutzer auf den Docker-Daemon und die laufenden
# Container einwirken koennen:
sudo usermod -aG docker $USER


# Die wichtigesten Docker Befehle:
docker version                      # zeigt die installierte Docker-Version an
docker run <Image>                  # startet einen Container und laedt das Image aus dem Docker-Hub
docker ps                           # zeigt alle laufenden Container
docker ps -a                        # zeigt alle Container, auch nicht laufende
docker image ls                     # zeigt heruntergeladene Images
docker image prune                  # loescht nicht verwendete Images
docker volume create <Name>         # erzeugt in Volume
docker volume prune                 # loaescht nicht verwendete Volumes (Vorsicht ist geboten!)
docker stop $(docker ps -a -q)      # haelt alle Container an
docker rm $(docker ps -a -q)        # loescht alle Container


# Damit Daten, die waehrend des Containerbetriebs anfallen, erhalten bleiben, sollten Sie diese in ein Volume auslagern.
# Ein solches muessen Sie zunaechst anlegen und ihm einen Namen geben:
docker volume create data 
# Das Volume data kann beim Starten mit "docker run" ueber "-v data:/usr/share/volume/data" uebergeben werden.
# /usr/share/volume/data ist der Pfad innerhalb des Containers.
# Beispiel: sudo bash ~/hdp/docker-deploy-hdp30_withVolumeData.sh (siehe angepasster docker run Befehl innerhalb der Datei.)

# Die Inhalte des Volumes speichert Docker im Unterverzeichnis /var/lib/docker/volumes und behaelt sie dort aus dann, wenn der
# zugehoerige Container laengst geloescht wurde.
# Ueber sudo -i kann man als root-User in das Verzeihnis /var/lib/docker/volumes schauen

# Wenn man ein Volume auf einer bestimmten Festplatte (Mount-Punkt) erstellen moechte, kann man dies bei der Erstellung
# des Volumes mit angeben. Siehe https://docs.docker.com/engine/reference/commandline/volume_create/#driver-specific-options

# Befehl innerhalb des Containers "sandbox-hdp" ausfuehren:
docker exec --privileged sandbox-hdp hdfs dfs -mkdir -p /tmp/data/