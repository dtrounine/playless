package io.github.eterverda.playless.common;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import io.github.eterverda.util.checksum.Checksum;

public final class Distribution {
    private final String applicationId;
    private final int versionCode;
    private final long timestamp;
    private final Checksum fingerprint;
    private final Checksum signatures;
    private final boolean debug;
    private final Map<String, String> meta;
    private final Requirements requirements;

    private Distribution(
            @NotNull String applicationId,
            int versionCode, long timestamp, @Nullable Checksum fingerprint,
            @NotNull Checksum signatures,
            boolean debug,
            @NotNull Map<String, String> meta,
            @NotNull Requirements requirements) {

        this.applicationId = applicationId;
        this.versionCode = versionCode;
        this.timestamp = timestamp;
        this.fingerprint = fingerprint;
        this.signatures = signatures;
        this.debug = debug;
        this.meta = meta;
        this.requirements = requirements;
    }

    public String applicationId() {
        return applicationId;
    }

    public int versionCode() {
        return versionCode;
    }

    public long timestamp() {
        return timestamp;
    }

    public Checksum fingerprint() {
        return fingerprint;
    }

    public Checksum signatures() {
        return signatures;
    }

    public boolean debug() {
        return debug;
    }

    public Map<String, String> meta() {
        return meta;
    }

    public Requirements requirements() {
        return requirements;
    }

    public static final class Requirements {
        private final int minSdkVersion;
        private final int maxSdkVersion;
        private final SortedSet<String> supportsScreens;
        private final SortedSet<String> compatibleScreens;
        private final SortedSet<String> supportsGlTextures;
        private final SortedSet<String> abis;
        private final SortedSet<String> usesFeatures;
        private final SortedSet<String> usesConfigurations;
        private final SortedSet<String> usesLibraries;

        private Requirements(
                int minSdkVersion, int maxSdkVersion,
                @NotNull SortedSet<String> supportsScreens,
                @NotNull SortedSet<String> compatibleScreens,
                @NotNull SortedSet<String> supportsGlTextures,
                @NotNull SortedSet<String> abis,
                @NotNull SortedSet<String> usesFeatures,
                @NotNull SortedSet<String> usesLibraries,
                @NotNull SortedSet<String> usesConfigurations) {

            this.minSdkVersion = minSdkVersion;
            this.maxSdkVersion = maxSdkVersion;
            this.supportsScreens = supportsScreens;
            this.compatibleScreens = compatibleScreens;
            this.supportsGlTextures = supportsGlTextures;
            this.abis = abis;
            this.usesFeatures = usesFeatures;
            this.usesLibraries = usesLibraries;
            this.usesConfigurations = usesConfigurations;
        }

        public int minSdkVersion() {
            return minSdkVersion;
        }

        public int maxSdkVersion() {
            return maxSdkVersion;
        }

        public Collection<String> supportsScreens() {
            return supportsScreens;
        }

        public Collection<String> compatibleScreens() {
            return compatibleScreens;
        }

        public Collection<String> supportsGlTextures() {
            return supportsGlTextures;
        }

        public Collection<String> abis() {
            return abis;
        }

        public Collection<String> usesFeatures() {
            return usesFeatures;
        }

        public Collection<String> usesConfigurations() {
            return usesConfigurations;
        }

        public Collection<String> usesLibraries() {
            return usesLibraries;
        }
    }

    public static final class Builder {
        private String applicationId;
        private int versionCode = 0;
        private long timestamp = Long.MIN_VALUE;
        private Checksum fingerprint;
        private Checksum signatures;
        private boolean debug;

        private final Map<String, String> meta = new HashMap<>();

        private int minSdkVersion = 1;
        private int maxSdkVersion = Integer.MAX_VALUE;
        private final SortedSet<String> supportsScreens = new TreeSet<>();
        private final SortedSet<String> compatibleScreens = new TreeSet<>();
        private final SortedSet<String> supportsGlTextures = new TreeSet<>();
        private final SortedSet<String> abis = new TreeSet<>();
        private final SortedSet<String> usesFeatures = new TreeSet<>();
        private final SortedSet<String> usesConfigurations = new TreeSet<>();
        private final SortedSet<String> usesLibraries = new TreeSet<>();

        public Builder applicationId(String applicationId) {
            this.applicationId = applicationId;
            return this;
        }

        public Builder versionCode(int versionCode) {
            this.versionCode = versionCode;
            return this;
        }

        public Builder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder fingerprint(Checksum fingerprint) {
            this.fingerprint = fingerprint;
            return this;
        }

        public void signature(Checksum... signatures) {
            for (Checksum signature : signatures) {
                this.signatures = Checksum.xor(this.signatures, signature);
            }
        }

        public Builder debug(boolean debug) {
            this.debug = debug;
            return this;
        }

        public Builder meta(String key, String value) {
            this.meta.put(key, value);
            return this;
        }

        public Builder minSdkVersion(int minSdkVersion) {
            this.minSdkVersion = minSdkVersion;
            return this;
        }

        public Builder maxSdkVersion(int maxSdkVersion) {
            this.maxSdkVersion = maxSdkVersion;
            return this;
        }

        public Builder supportsScreen(String... supportsScreens) {
            Collections.addAll(this.supportsScreens, supportsScreens);
            return this;
        }

        public Builder compatibleScreen(String... compatibleScreens) {
            Collections.addAll(this.compatibleScreens, compatibleScreens);
            return this;
        }

        public Builder supportsGlTexture(String... supportsGlTextures) {
            Collections.addAll(this.supportsGlTextures, supportsGlTextures);
            return this;
        }

        public Builder abi(String... abis) {
            Collections.addAll(this.abis, abis);
            return this;
        }

        public Builder usesFeature(String... usesFeatures) {
            Collections.addAll(this.usesFeatures, usesFeatures);
            return this;
        }

        public Builder usesConfiguration(String... usesConfigurations) {
            Collections.addAll(this.usesConfigurations, usesConfigurations);
            return this;
        }

        public Builder usesLibrary(String... usesLibraries) {
            Collections.addAll(this.usesLibraries, usesLibraries);
            return this;
        }

        public Distribution build() {
            return new Distribution(
                    applicationId,
                    versionCode,
                    timestamp,
                    fingerprint,
                    signatures,
                    debug,
                    Collections.unmodifiableMap(meta),
                    new Requirements(
                            minSdkVersion, maxSdkVersion,
                            Collections.unmodifiableSortedSet(supportsScreens),
                            Collections.unmodifiableSortedSet(compatibleScreens),
                            Collections.unmodifiableSortedSet(supportsGlTextures),
                            Collections.unmodifiableSortedSet(abis),
                            Collections.unmodifiableSortedSet(usesFeatures),
                            Collections.unmodifiableSortedSet(usesLibraries),
                            Collections.unmodifiableSortedSet(usesConfigurations)));
        }
    }
}
